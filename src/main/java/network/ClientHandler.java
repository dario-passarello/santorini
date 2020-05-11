package network;

import controller.Controller;
import network.messages.Message;
import network.messages.MessageTarget;
import network.messages.toserver.LoginDataMessage;
import network.messages.toserver.QuitGameMessage;
import view.ClientView;
import view.RemoteView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class ClientHandler implements Runnable, MessageTarget{

    private RemoteView remoteView;
    private final Socket clientSocket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private String name;
    private int playerNumber;


    public ClientHandler(Socket socket) throws IOException {
        clientSocket = socket;
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        handleConnection();
    }

    @SuppressWarnings("unchecked cast")
    private void handleConnection() {

        Object received;
        try {
            received = inputStream.readObject();
            ((LoginDataMessage) received).execute(this); //Set Login data (Username, Queue)
            Lobby waitingLobby = Lobby.getLobbyInstance(playerNumber);
            waitingLobby.findGame(this); //wait for players
            while(true) {
                received = inputStream.readObject();
                if(received instanceof QuitGameMessage) break;
                ((Message<RemoteView>) received).execute(this.remoteView);
            }
        }
        catch(ClassNotFoundException | ClassCastException exception ) {
            exception.printStackTrace();
        }
        catch(IOException e) {
            if(this.remoteView != null) {
                new QuitGameMessage().execute(this.remoteView);
            }
            e.printStackTrace();
        }
        try{
            clientSocket.close(); //Close the socket TODO verify socket closure responisbilty
        }
        catch (IOException e) {
            e.printStackTrace(); //TODO Logging
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
    public void sendMessage(Message<? extends MessageTarget> message) {
        try{
            outputStream.writeObject(message);
        }
        catch (IOException e) {
            if(this.remoteView != null) {
                new QuitGameMessage().execute(this.remoteView);
            }
            e.printStackTrace();
        }
    }

}
