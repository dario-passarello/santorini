package network;

import network.messages.Message;
import network.messages.MessageTarget;
import view.screens.Screen;
import view.ViewManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerAdapter implements Runnable{

    private final ViewManager view;
    private Socket socketToServer;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private final AtomicBoolean stopped;

    public ServerAdapter(ViewManager view, String serverIp, int port) throws IOException {
        stopped = new AtomicBoolean(false);
        this.view = view;
        socketToServer = new Socket(serverIp,port);
        outStream = new ObjectOutputStream(socketToServer.getOutputStream());
        inStream = new ObjectInputStream(socketToServer.getInputStream());
    }

    @Override
    public void run() {
        listen();
        try {
            socketToServer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void listen() {
        try{
            while (!stopped.get()){
                Message<Screen> mess = (Message<Screen>) inStream.readObject();
                view.receiveMessage(mess);
            }
        }
        catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();

        }
    }

    public void sendMessage(Message<? extends MessageTarget> messageToServer){
        try{
            outStream.writeObject(messageToServer);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        stopped.set(true);
    }



}
