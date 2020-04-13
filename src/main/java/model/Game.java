package model;

import model.gamestates.*;
import model.gods.God;
import utils.Coordinate;
import utils.Observer;
import utils.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


public class Game implements Observable, GameModel {


    private Integer maxPlayers;
    private List<Player> players;
    private Turn currentTurn;
    private Board board;
    private List<God> godList;

    private Player host;
    private Player winner;

    private GameState currentGameState;

    private List<Observer> observers;

    public final GameState setupState = new SetupState(this);
    public final GameState lobbyState = new LobbyState(this);
    public final GameState godSelectionState = new GodSelectionState(this);
    private List<GameState> pickGodStates = new ArrayList<>();
    private List<GameState> placeBuilderStates = new ArrayList<>();
    public final GameState turnState = new TurnState(this);
    public final GameState endGameState = new EndGameState(this);

    public static final int BUILDERS_PER_PLAYER = 2;


    public Game() {
        //TODO Build a board
        this.players = new ArrayList<>();
    }
    /**
     * Initializes a game, building the board and setting up all the things
     * @param playerNumber the number of player of this game
     */
    @Deprecated
    public Game(int playerNumber) {
        //TODO Build a board
        this.players = new ArrayList<>();
        maxPlayers = playerNumber;
    }

    /**
     *  @return a Reference to the game board
     */
    public Board getBoard() {
        return board;
    }
    /*
     *      +-----------------------------+
     *      | GETTERS AND SETTERS         |
     *      +-----------------------------+
     */
    /**
     * @return a copy of the list of the players participating at the game
     */
    public List<Player> getPlayers(){
        return new ArrayList<>(this.players);
    }

    /**
     * @return if the game ended an Optional containing the player, else an empty
     * optional
     */
    public Optional<Player> getWinner() {
        return Optional.ofNullable(winner);
    }
    /**
     * @return number of players in the game
     */
    public int playerCount() {
        return players.size();
    }
    /**
     * @return maximum number of players in this game
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }
    /*
     *      +-----------------------------+
     *      | STATE MACHINE INPUT METHODS |
     *      +-----------------------------+
     */
    public boolean configureGame(String hostPlayerName, int num){
        return currentGameState.configureGame(num, hostPlayerName);
    }

    public boolean registerPlayer(String playerName) {
        return currentGameState.registerPlayer(playerName);
    }

    public boolean unregisterPlayer(String playerName) {
        return currentGameState.unregisterPlayer(playerName);
    }

    public boolean readyToStart(){
        return currentGameState.readyToStart();
    }

    public boolean submitGodList(Set<String> godList) {
        return currentGameState.submitGodList(godList);
    }

    public boolean pickGod(String godName) {
        return currentGameState.pickGod(godName);
    }

    public boolean selectCoordinate(Coordinate coordinate) {
        return currentGameState.selectCoordinate(coordinate);
    }

    public boolean quitGame() {
        return currentGameState.quitGame();
    }

    /**
     * @return a reference to the current game state
     */
    public GameState getGameState() {
        return currentGameState; //TODO
    }

    /**
     * Set the current turn state
     * @param gameState a game state
     */
    public void setGameState(GameState gameState) {
        gameState.onExit();
        currentGameState = gameState;
        gameState.onEntry();
    }

    /**
     * Ends the current turn and advances to the next turn
     */
    public void nextTurn() {
        //TODO Define the behavior of this function (Player Ordering)
    }

    /**
     * @return the player that is playing the current turn
     */
    public Player getCurrentPlayer() {
        //TODO Get the player that is playing via the current Turn
        return null;
    }

    /**
     * @return A reference to the current turn object
     */
    public Turn getCurrentTurn() {
        //TODO
        return null;
    }

    public void setMaxPlayers(int max) {
        maxPlayers = max;
    }


    /**
     * Add a new player to the player list
     * @param name Name of the new player
     * @return Returns false if a player with the same name exists
     */
    public boolean createPlayer(String name) {
        if(players.stream().anyMatch(p -> p.getName().equals(name)) || players.size() >= maxPlayers)
            return false;
        else {
            players.add(new Player(this,name));
            return true;
        }
    }
    /**
     * Removes a player from the player list
     * @param name Name of the player to be removed
     * @return Returns false if a player with the same name exists
     */
    public boolean removePlayer(String name) {
        int prevSize = players.size();
        players = players.stream().filter(p -> !p.getName().equals(name) || host.getName().equals(name))
                .collect(Collectors.toList());
        return players.size() < prevSize;
    }

    public void setGodList(List<God> list) {
        godList = new ArrayList<>(list);
    }

    public void generatePickAndPlaceStates(){
        for(int i = 0; i < players.size(); i++){
            Player p = players.get(i);
            pickGodStates.add(0,new PickGodState(this,p,players.size() - i - 1));
            placeBuilderStates.add(0, new PlaceBuilderState(this,p,i));
        }
    }

    public GameState getPlaceBuilderState(int order){
        return placeBuilderStates.get(order);
    }

    public GameState getPickGodState(int order){
        return placeBuilderStates.get(order);
    }

    //Observable interface implementations
    public void addObserver(Observer o) {
        this.observers.add(o);
    }

    public List<God> getGodList() {
        return new ArrayList<>(godList);
    }

    public void deleteObserver(Observer o) {
        this.observers.remove(o);
    }

    public void notifyObservers() {
        observers.forEach(Observer::update);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
