package network;

import controller.Controller;
import model.Game;
import network.messages.toclient.MatchFoundMessage;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The matchmaking system for the server
 * Singleton pattern is used to ensure that only two instance of this class are created
 * (one for 2 players matches and one for 3 players matches)
 * When a Client Handler listener thread reaches out this class, it will wait enough players to
 * start a match. Match are created with a FIFO logic.
 */
public class Lobby implements Runnable {

    private static final Lobby twoPlayerMatchLobby = new Lobby(2);
    private static final Lobby threePlayerMatchLobby = new Lobby(3);

    private static final AtomicInteger gameID = new AtomicInteger(0);

    private final Queue<ClientHandler> waitingQueue;
    /**
     * The number of player of the lobby object
     */
    public final int numberOfPlayers;

    private Lobby(int numberOfPlayers) {
        this.waitingQueue = new LinkedList<>();
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * This method is the only way to get the lobby instances.
     * @param numberOfPlayers An integer, must be 2 or 3
     * @return Returns the lobby instance respectively for 2 and 3 player
     */
    public static Lobby getLobbyInstance(int numberOfPlayers) {
        if(numberOfPlayers == 2){
            return twoPlayerMatchLobby;
        }
        if(numberOfPlayers == 3) {
            return threePlayerMatchLobby;
        }
        throw new IllegalArgumentException("Matches could be only with 2 or 3 players!");
    }

    @Override
    public void run() {
        handleLobby();
    }

    /**
     * Remove a client handler previously waiting in a match queue. IF the handler is not
     * waiting in the queue nothing happens
     * @param handler The handler to remove from the queue
     */
    public synchronized void removeFromQueue(ClientHandler handler){
        waitingQueue.remove(handler);
    }

    private synchronized void handleLobby() {
        try{
            while(true) {
                while(waitingQueue.size() < this.numberOfPlayers){
                    wait();
                }
                List<ClientHandler> playerClients = new ArrayList<>();
                for(int i = 0; i < numberOfPlayers; i++){
                    playerClients.add(waitingQueue.poll());
                }
                sanitizeNames(playerClients); //Remove duplicates
                List<String> usernames = playerClients.stream()
                        .map(c -> c.getName().orElseThrow(IllegalStateException::new))
                        .collect(Collectors.toList());
                Game game = new Game(usernames, this.numberOfPlayers);
                Controller controller = new Controller(game);
                for(ClientHandler handler : playerClients){
                    //Register observers in the model
                    game.registerObserver(handler.getRemoteViewInstance(controller));
                    game.registerAllTurnObserver(handler.getRemoteViewInstance(controller));
                    //TODO Save controller on remote
                    handler.getRemoteViewInstance().setPlayerName(handler.getName().orElseThrow());
                    handler.sendMessage(new MatchFoundMessage(handler.getRemoteViewInstance().getPlayerName(),usernames)); //Notify that the match was created
                }
                gameID.incrementAndGet();
                game.start();
                notifyAll();
            }
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Helper method, checks if there are two duplicate names, in that case, the ClientHandler names
     * will be changed
     * @param clientHandlers A list of the clients in the match
     */
    private void sanitizeNames(List<ClientHandler> clientHandlers){
        Predicate<List<ClientHandler>> noDuplicates = (list) -> {
            List<String> names = list.stream()
                    .map(c -> c.getName().orElseThrow(IllegalStateException::new))
                    .collect(Collectors.toList());
            return names.size() == new HashSet<>(names).size();
        };
        Random random = new Random();
        while (!noDuplicates.test(clientHandlers)) {
            for(int i = 0; i < numberOfPlayers - 1; i++){
                for(int j = i + 1; j < numberOfPlayers; j++){
                    String firstName = clientHandlers.get(i).getName().orElseThrow(IllegalAccessError::new);
                    String secondName = clientHandlers.get(j).getName().orElseThrow(IllegalAccessError::new);
                    if(firstName.equals(secondName)) {
                        clientHandlers.get(j).setName(secondName + "\\" + (random.nextInt() % 99 + 1));
                    }
                }
            }
        }
    }

    /**
     * Add to the lobby queue the client handler. Wait until there are enough players for
     * a game. When a game is found the Game ID will be returned
     * NOTE: Usually called in the Client Handler listener thread context
     * @param client A reference of a client handler that wants to find a game
     * @return The id of the game
     */
    public synchronized int findGame(ClientHandler client) {
        waitingQueue.add(client);
        notifyAll();
        try{
            while(waitingQueue.contains(client))
                wait();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        return gameID.get();
    }

}
