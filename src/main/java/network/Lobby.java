package network;

import controller.Controller;
import model.Game;
import model.Player;
import network.messages.toclient.MatchFoundMessage;
import view.RemoteView;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class Lobby implements Runnable {

    private static final Lobby twoPlayerMatchLobby = new Lobby(2);
    private static final Lobby threePlayerMatchLobby = new Lobby(3);

    private final Queue<ClientHandler> waitingQueue;
    public final int numberOfPlayers;

    private Lobby(int numberOfPlayers) {
        this.waitingQueue = new LinkedList<>();
        this.numberOfPlayers = numberOfPlayers;
    }


    public static Lobby getLobbyInstance(int numberOfPlayers) {
        if(numberOfPlayers == 2){
            return twoPlayerMatchLobby;
        }
        if(numberOfPlayers == 3) {
            return threePlayerMatchLobby;
        }
        throw new IllegalArgumentException("Matches could be only with 2 or 3 players!");
    }

    //TODO Initialize on Server
    @Override
    public void run() {
        handleLobby();
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
                notifyAll();
            }
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

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

    //Called in clientHandler thread context
    public synchronized void findGame(ClientHandler client) {
        waitingQueue.add(client);
        notifyAll();
        try{
            while(waitingQueue.contains(client))
                wait();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }





}
