package network;

import controller.GameController;
import model.DuplicateNameException;
import model.Game;
import model.GameModel;

import java.rmi.Remote;
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

    public synchronized void acquire(Connection c) throws InterruptedException, DuplicateNameException {
        waitingConnection.add(c);
        notifyAll();
        createGame(c);
    }

    public boolean ready(Connection c){
        return (waitingConnection.size() >= numOfPlayer);
    }

    public synchronized void createGame(Connection c) throws InterruptedException, DuplicateNameException {
        while (!ready(c)) {                                                           //if there isn't enough player, wait
            wait();
        }


        List<RemoteView> remoteViews = new ArrayList<>();
        Connection player;
        for (int i = 0; i < numOfPlayer; i++) {
            player = waitingConnection.poll();
            remoteViews.add(new RemoteView(player));
        }

        GameController gameController = new GameController(remoteViews);
        gameController.startGame();

    }
}