package network;

import network.messages.Message;
import view.ClientView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ClientDriver implements Runnable{

    private Queue<Message<? extends ClientView>> messagesReceived;
    private final Socket socketToServer;
    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;

    public ClientDriver(String ip, int port) {
        messagesReceived = new LinkedList<>();
        try{
            socketToServer = new Socket(ip,port);
            outStream = new ObjectOutputStream(socketToServer.getOutputStream());
            inStream = new ObjectInputStream(socketToServer.getInputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Connection Problem");
        }
    }

    @Override
    public void run() {
        listen();
    }

    private void listen(){
        try{
            while (true){
                Message<? extends ClientView> mess = (Message<? extends ClientView>) inStream.readObject();
                synchronized (this) {
                    messagesReceived.offer(mess);
                    notifyAll();
                }
            }
        }
        catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();

        }

    }

    public void sendMessage(Message<?> message) {
        try{
            outStream.writeObject(message);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public synchronized List<Message<? extends ClientView>> receiveMessage() {
        try {
            while (messagesReceived.isEmpty()) {
                wait();
            }
            List<Message<? extends ClientView>> mess = new ArrayList<>();
            while(!messagesReceived.isEmpty()) {
                mess.add(messagesReceived.poll());
            }
            notifyAll();
            return mess;
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }


}
