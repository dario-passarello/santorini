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

/**
 * Client Side Handler for network messages to and from the server
 */
public class ServerAdapter implements Runnable, MessageTarget {

    private final int PING_INTERVAL_SEC = 10;

    private final ViewManager view;
    private final Socket socketToServer;
    private final ObjectOutputStream outStream;
    private final ObjectInputStream inStream;
    private final AtomicBoolean running;
    private Thread pinger;

    /**
     * Initializes the ServerAdapter
     * @param view A reference to the client's view manager
     * @param serverIp Address of the server
     * @param port Port of the server
     * @throws IOException Thrown if the connection to the server fails
     */
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
                Client.logger.info("Ping Interrupted");
            }
        }
    }

    /**
     * Starts the listener and the ping threads
     */
    @Override
    public void run() {
        pinger = new Thread(this::pingServer);
        pinger.start();
        listen();
        /*try {
            socketToServer.close();
        } catch (IOException e){
            //e.printStackTrace();
        }*/
    }

    @SuppressWarnings("Unchecked")
    private void listen() {
        try{
            while (running.get()){
                Message<Screen> messageReceived = (Message<Screen>) inStream.readObject();
                Client.logger.log(messageReceived.getLoggerLever(), "Message:" + messageReceived.getClass().getName() +
                        "\nContents:" + messageReceived.getMessageJSON());
                view.receiveMessage(messageReceived);
            }
        }
        catch (IOException | ClassNotFoundException | ClassCastException e) {
            Client.logger.warning("Receive message failed \nException: " + e.getClass().getName());
            executeCloseConnection();
            view.receiveMessage(new DisconnectClientMessage());
        }
    }

    private void executeCloseConnection() {
        running.set(false);
        if(pinger.isAlive())
            pinger.interrupt();
        try {
            socketToServer.close();
        } catch (IOException e){
            Client.logger.warning("Socket Already Closed");
        }
        Client.logger.warning("Connection Closed");
    }

    /**
     * Sends a {@link Message} to the server
     * @param messageToServer The message object with valid Server {@link MessageTarget}
     */
    public synchronized void sendMessage(Message<? extends MessageTarget> messageToServer){
        try{
            outStream.writeObject(messageToServer);
            outStream.reset();
        } catch(IOException e){
            Client.logger.warning("Send message " + messageToServer.getClass().getName() +" failed \n" +
                    "Exception: " + e.getClass().getName());
            executeCloseConnection();
            view.receiveMessage(new DisconnectClientMessage());
        }
    }

    /**
     * Closes the connection with the server
     */
    public synchronized void closeConnection(){
        Client.logger.info("Client requested connection closing");
        executeCloseConnection();
    }



}
