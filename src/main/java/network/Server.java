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

    private ExecutorService executor = Executors.newFixedThreadPool(128);

    private List<Connection> connections = new ArrayList<Connection>();         //all active connections in the server
    private List<ArrayList<Connection>> twoPlayingConnection = new ArrayList<ArrayList<Connection>>();
    private List<ArrayList<Connection>> threePlayingConnection = new ArrayList<ArrayList<Connection>>();

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    //Register connection
    public synchronized void registerConnection(Connection c){
        connections.add(c);
    }

    //Deregister connection
    public synchronized void deregisterConnection(Connection c, int numPlayer){
        connections.remove(c);
        List<ArrayList<Connection>> playingConnection;                  //it could be twoPlayingConnection or threePlayingConnection
        if(numPlayer == 2) {                                            //deregistering a two-player game
            playingConnection = twoPlayingConnection;
        } else {                                                        //deregistering a three-player game
            playingConnection = threePlayingConnection;
        }

        for(ArrayList<Connection> play : playingConnection){
            if(!play.contains(c)) continue;
            for(Connection player : play){
                player.closeConnection();
            }
            playingConnection.remove(play);
        }

        //Connection opponent = playingConnection.get(c);               EXAMPLE CODE
        //if(opponent != null){
        //    opponent.closeConnection();
        //    playingConnection.remove(c);
        //    playingConnection.remove(opponent);
        //}
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
