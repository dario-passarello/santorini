package model.turnstates;

import model.Builder;
import utils.Coordinate;

public interface TurnState {
    void onEntry();
    void onExit();
    /**
     * Give as an input a builder
     * @param b A reference to a builder controlled from the current player
     */
    boolean selectBuilder(Builder b);
    /**
     * Enable the use of a god power
     */
    boolean useGodPower();
    /**
     * Input a coordinate of the board
     * @param c A coordinate object representing the coordinate of a board
     */
    boolean selectCoordinate(Coordinate c);
    /**
     *  End the current turn phase and go to the next one
     */
    boolean endPhase();

}
