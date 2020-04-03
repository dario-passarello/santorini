package model;

import model.gamestates.GameState;
import model.gods.God;
import utils.Coordinate;
import utils.Observer;
import utils.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Game implements Observable, Cloneable {

    private Integer max_players;
    private List<Player> players;
    private Turn currentTurn;
    private Board board;
    private List<God> godList;

    private Player winner;

    private GameState currentGameState;


    private List<Observer> observers;


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
        max_players = playerNumber;
    }

    /**
     *  @return a Reference to the game board
     */
    public Board getBoard() {
        return board;
    }
    /*
     *      +-----------------------------+
     *      | PURE OBSERVERS METHODS      |
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
    public int maxPlayers() {
        return maxPlayers();
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
     */
    public void setNumberOfPlayers(int num){
    }
    /**
     * Adds new player to the list and then updates the game state
     *
     * @return true if the insertion of the player succeeded, false if the insertion failed because there
     * is another player with the same name or the game is full
     */
    public boolean addPlayer(Player p) {
        //TODO
        return false;
    }
    /**
     * Searches a player by name and removes it from the list and then updates the game state
     *
     * @return true if the player removal succeeded, false if the player is not
     * in the list or could not be removed
     */
    public boolean removePlayer(String playerName) {
        //TODO Remove player if exists

        return false;
    }

    /**
     * Copies a list of god in the game and updates the game state
     * @param godList The list of the names of the gods chosen for the game
     */
    public void submitGodList(List<String> godList) {
        //TODO
    }
    /**
     * Inputs coordinates in the game state (useful for builders placement phase)
     * @param player The player that is setting the coordinates
     * @param coordinate The coordinate given to the model
     */
    public void selectCoordinate(Player player, Coordinate coordinate) {
        //TODO
    }

    /**
     *  Quits the game
     */
    public void quitGame() {
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
    public void setTurnState(GameState gameState) {
        gameState.onExit();
        currentGameState = gameState;
        gameState.onEntry();
    }

    /**
     * @return true if the game is full and all players are ready to start
     */
    public boolean readyToStart() {
        //TODO
        return false;
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
