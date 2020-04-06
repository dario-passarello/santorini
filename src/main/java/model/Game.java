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

    public final GameState setupState = new EndGameState(this);
    public final GameState lobbyState = new LobbyState(this);
    public final GameState godSelectionState = new GodSelectionState(this);
    public final GameState pickGodState = new PickGodState(this);
    public final GameState placeBuilderState = new PlaceBuilderState(this);
    public final GameState turnState = new TurnState(this);
    public final GameState endGameState = new EndGameState(this);


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
     * @return true if this function call is legit for the current GameState
     */
    public boolean configureGame(String hostPlayerName, int num){
        return currentGameState.configureGame(num, hostPlayerName);
    }
    /**
     * Adds new player to the list and then updates the game state
     *
     * @return true if the insertion of the player succeeded, false if the insertion failed because there
     * is another player with the same name or the game is full
     */
    public boolean registerPlayer(Player p) {
        return currentGameState.registerPlayer(p);
    }
    /**
     * Searches a player by name and removes it from the list and then updates the game state
     *
     * @return true if this function call is legit for the current GameState and the player removal succeeded
     */
    public boolean unregisterPlayer(String playerName) {
        return currentGameState.unregisterPlayer(playerName);
    }

    public boolean readyToStart(){
        return currentGameState.readyToStart();
    }

    /**
     * Copies a list of god in the game and updates the game state
     * @param godList The list of the names of the gods chosen for the game
     * @return true if this function call is legit for the current GameState and if godList
     *  is a valid
     */
    public boolean submitGodList(Set<String> godList) {
        return currentGameState.submitGodList(godList);
    }
    /**
     * Inputs coordinates in the game state (useful for builders placement phase)
     * @param player The player that is setting the coordinates
     * @param coordinate The coordinate given to the model
     * @return true if this function call is legit for the current GameState and if
     *  the parameters are valid
     */
    public boolean selectCoordinate(Player player, Coordinate coordinate) {
        return currentGameState.selectCoordinate(player, coordinate);
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
