package model.turnstates;

import model.Turn;
import utils.Coordinate;

public interface TurnState {

    boolean firstSelection(int builderID, Coordinate c, boolean specialPower) throws IllegalArgumentException;

    boolean selectCoordinate(Coordinate c, boolean specialPower) throws IllegalArgumentException;

    /**
     *  End the current turn phase and go to the next one
     *  @return true if this is a legit action for the current state
     */
    boolean endPhase() throws IllegalStateException;

    Turn.State getStateID();
}
