package model;

import model.gamestates.GameState;
import utils.Observer;
import utils.Subject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class Game implements Subject, Observer {

    GameState lobby;
    GameState chooseMode;
    GameState chooseGods;
    GameState pickGods;
    GameState setupBoard;
    GameState placement;
    GameState turn;
    GameState endGame;


    private List<Player> players;
    private Player currentPlayer;
    private List<Observer> observers;
    private GameState state;
    private Board board;
    private final Integer MAX_PLAYERS;




    public Game(int playerNumber) {
        //Singeton
        this.players = new ArrayList<>();
        MAX_PLAYERS = playerNumber;
    }

    public Board getBoard() {
        return board;
    }

    /**
     * Adds new player to the list
     *
     * @return true if the player is added, false if the player is not added because there
     * is another player with the same name either the game is full
     */
    public boolean addPlayer(Player p) {
        //TODO Add game if the game is not full and there isn't a player with the same name of p
        return false;
    }

    /**
     * Searches a player by name and removes it from the list
     *
     * @return true if the player is correctly removed, false if the player is not
     * in the list
     */
    public boolean removePlayer(String playerName) {
        //TODO Remove player if exists

        return false;
    }

    /**
     * @return number of players in the game
     */
    public int playerCount() {
        return players.size();
    }

    /**
     * @return true if the game is full and all players are ready to start
     */
    public boolean readyToStart() {
        //TODO
        return false;
    }

    /**
     * Switches currentPlayer with the next player in list
     *
     * @return The player who has to play the next turn
     */
    public Player nextTurn() {
        //TODO Define the behavior of this function (Player Ordering)
        return null;
    }

    /**
     * @return The Player who is playing in the current turn
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    /**
     * @return The list of players currently playing the game
     */
    public List<Player> getPlayers(){
        return this.players;
    }

    //Observer interface implementations
    public void update() {

    }

    //Subject interface implementations
    public void addObserver(Observer o) {

    }

    public void deleteObserver(Observer o) {

    }

    public void notifyObservers() {
        observers.forEach(Observer::update);
    }
}
