package network;

import controller.Controller;
import network.messages.Message;
import network.messages.MessageTarget;
import network.messages.toclient.PingToClient;
import network.messages.toserver.LoginDataMessage;
import network.messages.toserver.DisconnectServerMessage;
import network.messages.toserver.PingToServer;
import view.RemoteView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static network.Server.logger;

/**
 * Handler for communications from/to each client. Is a layer under the {@link RemoteView}
 */
public class ClientHandler implements Runnable, MessageTarget {

    private final int PING_INTERVAL_SECONDS = 10;

    private RemoteView remoteView;
    private final Socket clientSocket;
    private String name;
    private int playerNumber;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private AtomicBoolean running;
    private int gameID = -1;

    /**
     * Creates a ClientHandler linked to a TCP Socket connection to a client
     * @param socket The socket that will be used to communicate with the client
     * @throws IOException Object streams could not be created from the socket
     */
    public ClientHandler(Socket socket) throws IOException {
        running = new AtomicBoolean(true);
        clientSocket = socket;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }


    @Override
    public void run() {
        handleConnection();
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pingClient() {
        while (running.get()) {
            sendMessage(new PingToClient());
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked cast")
    private void handleConnection() {
        Object received;
        try {
            do {
                received = inputStream.readObject();
            } while (!(received instanceof LoginDataMessage));
            ((LoginDataMessage) received).execute(this); //Set Login data (Username, Queue)
            logger.info(clientSocket.getInetAddress() + " logged as " + name);
            Lobby waitingLobby = Lobby.getLobbyInstance(playerNumber);
            new Thread(this::pingClient, "pinger_thread/" + clientSocket.getInetAddress()).start(); //STart
            gameID = waitingLobby.findGame(this); //wait for players
            while (running.get()) {
                received = inputStream.readObject();
                if (received instanceof DisconnectServerMessage) {
                    running.set(false); //Stop the listener
                    break;
                }
                if (received instanceof PingToServer) continue;
                Message<RemoteView> messageReceived = (Message<RemoteView>) received;
                logger.info("Game:" + gameID + " Message:" + messageReceived.getClass().getName() +
                        "\nContents:" + messageReceived.getMessageJSON());
                messageReceived.execute(this.remoteView); //Apply message content
            }
        } catch (ClassNotFoundException | ClassCastException | IOException e) {
            handleConnectionError(e);
        }
    }

    /**
     * Get the {@link RemoteView} instance for this ClientHandler.
     * If it a remoteView is not present new one will be created.
     * @param controller The controller used from the RemoteView
     * @return The instance of the remote view generated from this ClientHandler
     */
    public RemoteView getRemoteViewInstance(Controller controller) {
        if (remoteView == null) {
            remoteView = new RemoteView(this, controller, name);
        }
        return remoteView;
    }

    /**
     * Get the {@link RemoteView} instance for this ClientHandler.
     * @return The instance of the remote view generated from this ClientHandler
     * @throws IllegalStateException if the reference to the RemoteView is not present
     */
    public RemoteView getRemoteViewInstance() {
        if (remoteView == null) {
            throw new IllegalStateException();
        }
        return remoteView;
    }

    /**
     * Getter for the client's username. If the client has not already sent the {@link LoginDataMessage}, then the
     * optional would be empty
     * @return An optional containing the username of the client
     */
    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    /**
     * Setter for the client's username
     * @param name A string containing the new client
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the progressive number of the player
     * @return The progressive number of the player
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Setter for the progressive number of the player
     * @param playerNumber The new progressive number of the player
     */
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    //Executed in the context of the caller
    /**
     * Sends a {@link Message} object to the client. If the message could not be delivered to the player
     * due to a disconnection the disconnection of the player will be handled
     * @param message The Message object to send to the player with a proper target
     */
    public synchronized void sendMessage(Message<? extends MessageTarget> message) {
        try {
            outputStream.writeObject(message);
            outputStream.reset();
        } catch (IOException e) {
            handleConnectionError(e);
        }
    }

    private void handleConnectionError(Exception e) {
        StringBuilder error = new StringBuilder();
        error.append("Connection with ").append(clientSocket.getInetAddress()).append(" dropped");
        running.set(false); //Stop running
        if (playerNumber == 2 || playerNumber == 3) {
            Lobby.getLobbyInstance(playerNumber).removeFromQueue(this); //Remove from queue if present
        }
        if (gameID >= 0) {
            error.append("\nGame ID: ").append(gameID);
        }
        if (this.remoteView != null) {
            error.append("\nPlayer info: ").append(remoteView.getPlayerName());
            new DisconnectServerMessage().execute(this.remoteView);
        }
        error.append("\nNetwork error type: ").append(e.getClass().getName());
        Server.logger.warning(error.toString());
        //e.printStackTrace();
    }

    /**
     * Closes the connection to the client handling the disconnection
     */
    public synchronized void closeConnection() {
        running.set(false);
    }
}
