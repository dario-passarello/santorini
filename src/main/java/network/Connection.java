package network;

import model.DuplicateNameException;
import utils.Message;
import utils.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection /*extends Observable<String>*/ implements Runnable {        //TODO connection should be observable

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Server server;
    private String name;
    private boolean active = true;
    private int numPlayer;

    public Connection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive(){
        return active;
    }


    public void send(Message message) {
        if(!active) return;                         // If there's not an active connection, there's nothing to send TODO better
        try{
            out.writeObject(message);
        }
        catch(IOException exception){
            //TODO update log
        }
    }

    /* public void asyncSend(final String message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

     */

    public synchronized void closeConnection(){
        try{
            socket.close();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        active = false;
    }

    private void close(int numPlayer){
        closeConnection();
        send(new Message(null, MessageType.CLIENT_DISCONNECTION));
        server.deregisterConnection(this, numPlayer);
        send(new Message(null, MessageType.CLIENT_DISCONNECTED));
    }

    private String getName(){
        return name;
    }

    @Override
    public void run() {
        try{
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            /* send("Welcome! What's your name?");
            name = in.nextLine();
            send("Choose the number of players (2 or 3)\n");
            numPlayer = in.nextInt();
            if(numPlayer != 2 && numPlayer != 3){
                send("Error! invalid number");                            //we should ask again numPlayer (TO DO)
            }

             */
            Lobby.getLobbyInstance(numPlayer).acquire(this);
            while(isActive()){
                //HERE WE SHOULD LINK TO THE FSM

                //TODO
                break;
                //String read = in.nextLine();
                //notify(read);
            }
        } catch(IOException | InterruptedException | DuplicateNameException e){
            System.err.println(e.getMessage());
        } finally {
            close(numPlayer);
        }
    }


}
