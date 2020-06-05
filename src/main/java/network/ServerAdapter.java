package network;

import network.messages.Message;
import network.messages.MessageTarget;
import network.messages.toclient.DisconnectClientMessage;
import network.messages.toclient.PingToClient;
import network.messages.toserver.PingToServer;
import view.screens.Screen;
import view.ViewManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerAdapter implements Runnable, MessageTarget {

    private final int PING_INTERVAL_SEC = 10;

    private final ViewManager view;
    private final Socket socketToServer;
    private final ObjectOutputStream outStream;
    private final ObjectInputStream inStream;
    private final AtomicBoolean running;

    public ServerAdapter(ViewManager view, String serverIp, int port) throws IOException {
        running = new AtomicBoolean(true);
        this.view = view;
        socketToServer = new Socket(serverIp,port);
        outStream = new ObjectOutputStream(socketToServer.getOutputStream());
        inStream = new ObjectInputStream(socketToServer.getInputStream());
    }

    private void pingServer(){
        while(running.get()){
            sendMessage(new PingToServer());
            try{
                TimeUnit.SECONDS.sleep(PING_INTERVAL_SEC);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        listen();
        new Thread(this::pingServer).start();
        try {
            socketToServer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Unchecked")
    private void listen() {
        try{
            while (running.get()){
                Message<Screen> messageReceived = (Message<Screen>) inStream.readObject();
                Client.logger.info("Message:" + messageReceived.getClass().getName() +
                        "\nContents:" + messageReceived.getMessageJSON());
                view.receiveMessage(messageReceived);
            }
        }
        catch (IOException | ClassNotFoundException | ClassCastException e) {
            running.set(false);
            view.receiveMessage(new DisconnectClientMessage());
        }
    }

    public void sendMessage(Message<? extends MessageTarget> messageToServer){
        try{
            outStream.writeObject(messageToServer);
            outStream.reset();
        } catch(IOException e){
            running.set(false); //Stop the listener
            view.receiveMessage(new DisconnectClientMessage());
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        running.set(false);
    }



}
