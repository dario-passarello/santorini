package model;

import model.turnstates.TurnState;
import utils.Coordinate;

public class Turn {




    private Game game;
    private TurnState currentState;
    private Player currentPlayer;
    public Turn(Player currentPlayer) {

    }

    /**
     *
     * @return a reference to the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @return a reference to the current state
     */
    public TurnState getTurnState() {
        return null; //TODO
    }

    /**
     * Set the current turn state
     * @param turn a turn state
     */
    public void setTurnState(TurnState turn) {

    }

    /**
     * Give as an input a builder
     * @param b A reference to a builder controlled from the current player
     */
    public void selectBuilder(Builder b) {
        //TODO
    }

    /**
     * Enable the use of a god power
     */
    public void useGodPower() {
        //TODO
    }

    /**
     * Input a coordinate of the board
     * @param c A coordinate object representing the coordinate of a board
     */
    public void selectCoordinate(Coordinate c) {
        //TODO
    }

    /**
     *  End the current turn phase and go to the next one
     */
    public void endPhase() {
        //TODO
    }

}
