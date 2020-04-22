package network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection extends Observable<String> implements Runnable {

    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private Server server;
    private String name;
    private boolean active = true;

    public Connection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive(){
        return active;
    }

    public void send(String message){
        out.println(message);
        out.flush();
    }

    public void asyncSend(final String message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    public synchronized void closeConnection(){
        send("Connection closed from the server side");
        try{
            socket.close();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        active = false;
    }

    private void close(int numPlayer){
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this, numPlayer);
        System.out.println("Done!");
    }

    private String getName(){
        return name;
    }

    public int getNumPlayer() {
        return numPlayer;
    }

    @Override
    public void run() {
        try{
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
            send("Welcome! What's your name?");
            name = in.nextLine();
            send("Choose the number of players (2 or 3)\n");
            numPlayer = in.nextInt();
            if(numPlayer != 2 && numPlayer != 3){
                send("Error! invalid number");                            //we should ask again numPlayer (TO DO)
            }
            Lobby.getLobbyInstance(numPlayer).acquire(this);


            while(isActive()){
                //HERE WE SHOULD LINK TO THE FSM

                //String read = in.nextLine();
                //notify(read);
            }
        } catch(IOException | InterruptedException e){
            System.err.println(e.getMessage());
        } finally {
            close(numPlayer);
        }
    }
}
