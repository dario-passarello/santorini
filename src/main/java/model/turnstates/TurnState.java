package model.turnstates;

import model.Builder;
import model.Turn;
import utils.Coordinate;

public interface TurnState {

    boolean firstSelection(Builder b, Coordinate c, boolean specialPower);

    default boolean firstSelection(Builder b, Coordinate c) {
        return firstSelection(b, c, false);
    }

    boolean selectCoordinate(Coordinate c, boolean specialPower);

    /**
     * Select Power
     * @param c
     * @return
     */
    default boolean selectCoordinate(Coordinate c) {
        return selectCoordinate(c, false);
    }

    /**
     *  End the current turn phase and go to the next one
     *  @return true if this is a legit action for the current state
     */
    boolean endPhase();

    Turn.State getStateID();
}
