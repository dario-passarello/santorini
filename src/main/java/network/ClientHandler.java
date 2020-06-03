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

public class ClientHandler implements Runnable, MessageTarget{

    private final int PING_INTERVAL_SECONDS = 10;

    private RemoteView remoteView;
    private final Socket clientSocket;
    private String name;
    private int playerNumber;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private AtomicBoolean running;


    public ClientHandler(Socket socket) throws IOException {
        running = new AtomicBoolean(true);
        clientSocket = socket;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        handleConnection();
        try{
            clientSocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pingClient(){
        while(running.get()){
            sendMessage(new PingToClient());
            try{
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked cast")
    private void handleConnection() {
        Object received;
        try {
            do{
                received = inputStream.readObject();
            } while (!(received instanceof LoginDataMessage));
            ((LoginDataMessage) received).execute(this); //Set Login data (Username, Queue)
            logger.info(clientSocket.getInetAddress() + " logged as " + name);
            Lobby waitingLobby = Lobby.getLobbyInstance(playerNumber);
            new Thread(this::pingClient,"pinger_thread/" + clientSocket.getInetAddress()).start(); //STart
            waitingLobby.findGame(this); //wait for players
            while(running.get()) {
                received = inputStream.readObject();
                if(received instanceof DisconnectServerMessage) {
                    running.set(false); //Stop the listener
                    break;
                }
                if(received instanceof PingToServer) continue;
                Message<RemoteView> messageReceived = (Message<RemoteView>) received;
                logger.info("Message:" + messageReceived.getClass().getName() +
                        "\nContents:" + messageReceived.getMessageJSON());
                messageReceived.execute(this.remoteView); //Apply message content
            }
        }
        catch(ClassNotFoundException | ClassCastException | IOException e) {
            handleConnectionError(e);
        }
    }


    public RemoteView getRemoteViewInstance(Controller controller) {
        if(remoteView == null) {
            remoteView = new RemoteView(this, controller, name);
        }
        return remoteView;
    }

    public RemoteView getRemoteViewInstance() {
        if(remoteView == null) {
            throw new IllegalStateException();
        }
        return remoteView;
    }



    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    //Executed in the context of the caller
    public synchronized void sendMessage(Message<? extends MessageTarget> message) {
        try{
            outputStream.writeObject(message);
            outputStream.reset();
        }
        catch (IOException e) {
            handleConnectionError(e);
        }
    }

    private void handleConnectionError(Exception e) {
        StringBuilder error = new StringBuilder();
        error.append("Connection with ").append(clientSocket.getInetAddress()).append(" dropped");
        running.set(false); //Stop running
        if(playerNumber == 2 || playerNumber == 3){
            Lobby.getLobbyInstance(playerNumber).removeFromQueue(this); //Remove from queue if present
        }
        if(this.remoteView != null) {
            error.append("\nPlayer info: ").append(remoteView.getPlayerName());
            new DisconnectServerMessage().execute(this.remoteView);
        }
        Server.logger.warning(error.toString());
        e.printStackTrace();
    }

    public synchronized void closeConnection(){
        running.set(false);
    }
}
