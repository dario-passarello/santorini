package model;

import model.gamestates.*;
import model.gods.God;
import utils.Coordinate;
import utils.Observable;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class Game implements Observable<GameObserver>, GameModel {

    public enum State implements StateIdentifier{
        GOD_SELECTION,
        GOD_PICK,
        PLACE_BUILDER,
        TURN,
        END_GAME
    }

    private final Board board;
    private List<God> godList;

    private final List<Player> players;
    private Player winner;

    private Turn currentTurn;
    private final List<Turn> turnRotation;

    private GameState currentGameState;

    private final List<GameObserver> observers;

    public final GameState godSelectionState = new GodSelectionState(this);
    public final GameState godPickState = new GodPickState(this);
    public final GameState placeBuilderState = new PlaceBuilderState(this);
    public final GameState turnState = new TurnState(this);
    public final GameState endGameState = new EndGameState(this);

    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 3;

    /**
     * Initializes a game, building the board and setting up all the things
     */
    public Game(List<String> names, int maxPlayers) throws DuplicateNameException {
        if(maxPlayers < MIN_PLAYERS || maxPlayers > MAX_PLAYERS || names.size() != maxPlayers) {
            throw new IllegalArgumentException(ErrorMessage.PLAYER_NUMBER_ERROR);
        }
        if(names.stream().anyMatch(name -> Collections.frequency(names, name) > 1)) {
            throw new DuplicateNameException();
        }
        players = names.stream()
                .map(name -> new Player(this, name))
                .collect(Collectors.toList());
        turnRotation = players.stream()
                .map(p -> new Turn(this,p))
                .collect(Collectors.toList());
        this.currentTurn = turnRotation.get(0);
        this.board = new Board(this);
        this.godList = new ArrayList<>();
        this.observers = new ArrayList<>();

    }

    public void start(){
        this.setGameState(this.godSelectionState, getFirstPlayer());
    }

    //GETTERS

    /**
     * @return a Reference to the game board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return a copy of the list of the players participating at the game
     */
    public List<Player> getPlayers() {
        return new ArrayList<>(this.players);
    }

    /**
     * @return the first player to place builder
     */
    public Player getFirstPlayer() {
        return players.get(0);
    }
    /**
     * @return the last player to place builder
     */
    public Player getLastPlayer() {
        return players.get(players.size() - 1);
    }

    /**
     * @return if the game ended an Optional containing the player, else an empty
     * optional
     */
    public Optional<Player> getWinner() {
        return Optional.ofNullable(winner);
    }

    /**
     * @return number of total players
     */
    public int getNumberOfPlayers() {
        return players.size();
    }

    /**
     * @return a reference to the current game state
     */
    public GameState getGameState() {
        return currentGameState;
    }

    public List<God> getGodList() {
        return new ArrayList<>(godList);
    }

    /**
     * @return A reference to the current turn object
     */
    public Turn getCurrentTurn() {
        return currentTurn;
    }

    public List<Player> getPlayersInGame() {
        return players.stream().filter(p -> p.getStatus().isAlive()).collect(Collectors.toList());
    }

    public List<Builder> getAllBuilders() {
        List<Builder> builderList = new ArrayList<>();
        getPlayersInGame().forEach(p -> builderList.addAll(p.getBuilders()));
        return builderList;
    }


    //SETTERS
    /**
     * List
     * @param list the list of gods chosen by the host for this game
     */
    public void setGodList(List<God> list) {
        godList = new ArrayList<>(list);
    }

    /**
     * Set the current turn state and send message to observers
     * @param gameState a game state
     * @param activePlayer Active Player
     */
    public void setGameState(GameState gameState, Player activePlayer) {
        currentGameState = gameState;
        notifyObservers(g -> g.receiveGameState(currentGameState.getStateIdentifier(), new Player(activePlayer)));
    }

    public void setWinner(Player winner) {
        this.winner = winner;
        setGameState(endGameState, winner);
    }

    //MODIFIERS

    /**
     * Ends the current turn and advances to the next turn
     */
    public void nextTurn(boolean firstTurn) {
        if(!firstTurn) {
            Collections.rotate(turnRotation, -1);
        }
        currentTurn = turnRotation.get(0);
        currentTurn.newTurn(); //initializes the new turn
    }

    public void removePlayer(String playerName, boolean disconnected) {
        removePlayer(players.stream()
                .filter(p -> p.getName().equals(playerName))
                .findAny().orElseThrow(IllegalArgumentException::new),disconnected);
    }

    public void removePlayer(Player player, boolean disconnected) {
        if(!players.contains(player)) {
            throw new NoSuchElementException(ErrorMessage.PLAYER_NOT_FOUND);
        }
        if(!player.getStatus().isAlive()) {
            throw new IllegalArgumentException(ErrorMessage.PLAYER_ALREADY_REMOVED);
        }
        player.setStatus(disconnected ? Outcome.DISCONNECTED : Outcome.LOSER);
        if(currentGameState != turnState){
            setGameState(endGameState, player);
        } else {
            if(getPlayersInGame().size() == 1) { //If Only two players remain
                setWinner(getPlayersInGame().get(0));
                setGameState(endGameState, player);
            }
            else {
                Turn removeTurn = currentTurn;
                if (turnRotation.stream() //If the player removed is playing in the current turn, advance to next turn
                        .filter(t -> t.getCurrentPlayer().equals(player))
                        .findFirst()
                        .orElseThrow(UnknownError::new) == currentTurn) {
                    nextTurn(false);
                }
                turnRotation.remove(removeTurn); //Remove player from turn rotation
            }
        }
        notifyObservers(GameObserver::receiveUpdateDone);
    }

    //STATE MACHINE METHODS

    @Override
    public boolean submitGodList(Set<String> godList) throws IllegalArgumentException {
        return currentGameState.submitGodList(godList);
    }

    @Override
    public boolean pickGod(String playerName, String godName) throws IllegalArgumentException{
        return currentGameState.pickGod(playerName, godName);
    }

    @Override
    public boolean selectCoordinate(String playerName, Coordinate coordinate) throws IllegalArgumentException {
        return currentGameState.selectCoordinate(playerName, coordinate);
    }

    @Override
    public boolean quitGame(String playerName) {
        return currentGameState.quitGame(playerName);
    }

    @Override
    public Game.State getStateIdentifier(){
        return currentGameState.getStateIdentifier();
    }

    //OBSERVABLE INTERFACE IMPLEMENTATION

    @Override
    public void registerObserver(GameObserver m) {
        observers.add(m);
    }

    public void registerAllTurnObserver(TurnObserver obs) {
        turnRotation.forEach(turn -> turn.registerObserver(obs));
    }

    @Override
    public void unregisterObserver(GameObserver m) {
        observers.remove(m);
    }

    @Override
    public void notifyObservers(Consumer<GameObserver> notifyAction) {
        observers.forEach(notifyAction);
    }


}
