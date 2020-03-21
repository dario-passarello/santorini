package model;

import model.gamestates.GameState;
import utils.Observer;
import utils.Subject;

import java.util.ArrayList;
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




    public Game() {
        //Singeton
        this.players = new ArrayList<Player>();
    }

    //TODO Getboard ???


    public void addPlayer(Player p) {

    }

    public void removePlayer(Player p) {

    }

    /**
     *
     *
     * @return The player who has to play the next turn
     */
    public Player nextTurn() {

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
