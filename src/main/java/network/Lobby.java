package network;

import model.Game;
import model.GameModel;

import java.util.*;

public class Lobby {
    private final int numOfPlayer;
    //private Server server;

    private static final Lobby twoPlayerLobby = new Lobby(2);
    private static final Lobby threePlayerLobby = new Lobby(3);
    private Queue<Connection> waitingConnection = new LinkedList<Connection>();

    private Lobby(int numOfPlayer){
        this.numOfPlayer = numOfPlayer;
    }

    public static Lobby getLobbyInstance(int numOfPlayer){
        if(numOfPlayer == 2){
            return twoPlayerLobby;
        }
        if(numOfPlayer == 3){
            return threePlayerLobby;
        }

        throw new IllegalArgumentException("Lobby can have only 2 or 3 players");
    }

    public synchronized void acquire(Connection c) throws InterruptedException {
        waitingConnection.add(c);
        notifyAll();
        createGame(c);
    }

    public boolean ready(Connection c){
        return (waitingConnection.size() >= numOfPlayer);
    }

    public synchronized void createGame(Connection c) throws InterruptedException {
        while (!ready(c)) {                                                           //if there isn't enough player, wait
            wait();
        }

        Set<Connection> players = new HashSet<>();
        for (int i = 0; i < numOfPlayer; i++) {
            players.add(waitingConnection.poll());
        }
        //TODO
        /*
        RemoteView remoteView = new RemoteView();            //what should we pass to the RemoteView constructor?
        GameModel model = new Game();
        Controller controller = new Controller(model);
        model.addObserver(remoteView);
        remoteView.addObserver(controller);
        */
    }
}