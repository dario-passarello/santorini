package network;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT= 12345;
    private ServerSocket serverSocket;

    private Lobby lobby;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        this.lobby = new Lobby();
    }

    private ExecutorService executor = Executors.newFixedThreadPool(128);

    private List<Connection> connections = new ArrayList<Connection>();         //all active connections in the server

    private List<ArrayList<Connection>> twoPlayingConnection = new ArrayList<ArrayList<Connection>>();
    private List<ArrayList<Connection>> threePlayingConnection = new ArrayList<ArrayList<Connection>>();

    //Register connection
    private synchronized void registerConnection(Connection c){
        connections.add(c);
    }

    //Deregister connection
    public synchronized void deregisterConnection(Connection c){
        connections.remove(c);
        if(c.getNumPlayer() == 2){                   //deregistering a two-player game
            //browse twoPlayingConnection and remove every Pair of Connection that contains c (calling closeConnection first)
        } else {
            //browse threePlayingConnection and remove every Triplet of Connection that contains c (calling closeConnection first)
        }

        //Connection opponent = playingConnection.get(c);               OLD CODE
        //if(opponent != null){
        //    opponent.closeConnection();
        //    playingConnection.remove(c);
        //    playingConnection.remove(opponent);
        //}
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void run(){
        System.out.println("Server listening on port: " + PORT);
        while(true){
            try {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket, this);
                registerConnection(connection);
                executor.submit(connection);
            } catch (IOException e){
                System.err.println("Connection error!");
            }
        }
    }

}
