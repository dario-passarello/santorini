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


public class Game implements Observable, Cloneable {


    private Integer maxPlayers;
    private List<Player> players;
    private Turn currentTurn;
    private Board board;
    private List<God> godList;

    private Player host; //TODO
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
    /**
     *
     * Sets number of players and then updates the game state
     * @param num Number of players
     * @param hostPlayerName Name of the player who hosts this game
     * @return true if true if this function call is legit for the current GameState function call is legit for the current GameState
     */
    public boolean configureGame(String hostPlayerName, int num){
        return currentGameState.configureGame(num, hostPlayerName);
    }
    /**
     * Adds a new player waiting in the lobby and updates the game state
     * @param playerName name of the player
     * @return false if there is another player with the same name of one of the player in the lobby
     * or if the lobby is full.
     */
    public boolean registerPlayer(String playerName) {
        return currentGameState.registerPlayer(playerName);
    }
    /**
     * Searches a player by name and removes it from the lobby and then updates the game state
     * @return true if the function call is legit for the current GameState and the player removal succeeded
     */
    public boolean unregisterPlayer(String playerName) {
        return currentGameState.unregisterPlayer(playerName);
    }

    /**
     * Starts the game after all players are in lobby
     * @return true if the are enough players to start the game
     * and if the function call is legit for the current GameState
     */
    public boolean readyToStart(){
        return currentGameState.readyToStart();
    }

    /**
     * Receives the list of the names of the gods chosen by the host for the game
     * @param godList The list of the names of the gods chosen for the game, it should contain
     *                a number of valid god names equal to the number of the player in the game or
     *                it could be left empty (in this case a game with 3 "mortals" will be initialized
     * @return true if this function call is legit for the current GameState and if godList
     *  is a valid list
     */
    public boolean submitGodList(Set<String> godList) {
        return currentGameState.submitGodList(godList);
    }

    /**
     * Receive the godPick from the player
     * @param godName
     * @return true if this function call is legit for the current GameState
     */
    public boolean pickGod(String godName) {
        return currentGameState.pickGod(godName);
    }
    /**
     * Inputs coordinates in the game state (useful for builders placement phase)
     * @param coordinate The coordinate given to the model
     * @return true if this function call is legit for the current GameState and if
     *  the parameters are valid
     */
    public boolean selectCoordinate(Coordinate coordinate) {
        return currentGameState.selectCoordinate(coordinate);
    }

    /**
     *  Quits the game
     *  @return true if this function call is legit for the current GameState
     */
    public boolean quitGame() {
        return currentGameState.quitGame();
        //TODO
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

    public List<God> getGodList() {
        return new ArrayList<>(godList);
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

    //Subject interface implementations
    public void addObserver(Observer o) {
        //TODO Implement observer pattern
    }

    public void deleteObserver(Observer o) {
        //TODO Implement observer pattern
    }

    public void notifyObservers() {
        observers.forEach(Observer::update);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
