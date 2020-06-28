package model;

import model.gamestates.*;
import model.gods.God;
import utils.Coordinate;
import utils.Observable;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;


/**
 * A match at the game "Santorini" between two or three {@link Player}
 */
public class Game implements Observable<GameObserver>, GameModel {

    /**
     * an identifier for the game phase
     */
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

    /**
     * god selection state reference of the Game FSM
     */
    public final GameState godSelectionState = new GodSelectionState(this);

    /**
     * god pick state reference of the Game FSM
     */
    public final GameState godPickState = new GodPickState(this);

    /**
     * place builder state reference of the Game FSM
     */
    public final GameState placeBuilderState = new PlaceBuilderState(this);

    /**
     * turn state reference of the Game FSM
     */
    public final GameState turnState = new TurnState(this);

    /**
     * end game state reference of the Game FSM
     */
    public final GameState endGameState = new EndGameState(this);

    /**
     * minimun number of player per Game
     */
    public static final int MIN_PLAYERS = 2;
    /**
     * maximum number of player per Game
     */
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

    /**
     * start a Game
     */
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
     * @return a copyBehavior of the list of the players participating at the game
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

    /**
     * @return the list of players that are still playing
     */
    public List<Player> getPlayersInGame() {
        return players.stream().filter(p -> p.getStatus().isActive()).collect(Collectors.toList());
    }

    /**
     * @return the list of builders currently on the Board
     */
    public List<Builder> getAllBuilders() {
        List<Builder> builderList = new ArrayList<>();
        getPlayersInGame().forEach(p -> builderList.addAll(p.getBuilders()));
        return builderList;
    }

    /**
     * @return the list of player shifted by one
     */
    public List<Player> getPlayersTurnOrder() {
        ArrayList<Player> p = new ArrayList<>();
        turnRotation.forEach(t -> p.add(t.getCurrentPlayer()));
        return p;
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

    /**
     * @param winner the player that have won the Game
     */
    public void setWinner(Player winner) {
        this.winner = winner;
        winner.setStatus(Outcome.WINNER);
        players.stream().filter(p -> !p.equals(winner)).forEach(p -> p.setStatus(Outcome.LOSER));
        notifyObservers(obs -> obs.receivePlayerList(getPlayers()));
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

    /**
     * @param playerName the name of a player in the Game
     * @param disconnected indicates if playerName is disconnected
     */
    public void removePlayer(String playerName, boolean disconnected) {
        removePlayer(players.stream()
                .filter(p -> p.getName().equals(playerName))
                .findAny().orElseThrow(IllegalArgumentException::new),disconnected);
    }

    /**
     * @param player a player in the Game
     * @param disconnected indicates if player is disconnected
     */
    public void removePlayer(Player player, boolean disconnected) {
        if(!players.contains(player)) {
            throw new NoSuchElementException(ErrorMessage.PLAYER_NOT_FOUND);
        }
        if(!player.getStatus().isActive()) {
            return;
            //throw new IllegalArgumentException(ErrorMessage.PLAYER_ALREADY_REMOVED);
        }
        if(player.getStatus() == Outcome.IN_GAME){
            player.getBuilders().forEach(Builder::removeBuilder);
        }
        if(currentGameState != endGameState) { //If the game is ended don't do anything
            player.setStatus(disconnected ? Outcome.DISCONNECTED : Outcome.LOSER);
            if(currentGameState != turnState){
                setGameState(endGameState, player);
            } else {
                if(getPlayersInGame().size() == 1) { //If Only two players remain
                    setWinner(getPlayersInGame().get(0));
                    setGameState(endGameState, player);
                }
                else {
                    Turn turnToRemove = turnRotation.stream()
                            .filter(t -> t.getCurrentPlayer().equals(player))
                            .findFirst()
                            .orElseThrow(UnknownError::new);
                    if (turnToRemove == currentTurn) {//If the player removed is playing in the current turn, advance to next turn
                        nextTurn(false);
                    }
                    turnRotation.remove(turnToRemove); //Remove player from turn rotation
                }
            }
            notifyObservers(GameObserver::receiveUpdateDone);
        }

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

    public void unregisterAllTurnObservers(TurnObserver obs) {
        turnRotation.forEach(turn -> turn.unregisterObserver(obs));
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
