package model.turnstates;

import model.Turn;
import utils.Coordinate;

/**
 * Interface for the states of the Turn State Machine
 */
public interface TurnState {

    /**
     * This method handles the first action of the turn. The action could either be a regular move or a special action
     * @param builderID The parameter representing which builder has been selected to execute the action
     * @param coordinate The coordinate the player has decided to be the target of the action
     * @param specialPower The parameter which represent if the action is a special one or not
     * @return True if this method is called from the correct state. False otherwise
     * @throws IllegalArgumentException when the parameters are invalid or when they are not consistent with the state
     * of the game
     */
    boolean firstSelection(int builderID, Coordinate coordinate, boolean specialPower) throws IllegalArgumentException;

    /**
     * This method handles every action that requires a coordinate to be executed. It could mean different things, depending
     * on the current state of the state machine
     * @param coordinate The coordinate target
     * @param specialPower The parameter which represent if the action is a special one or not
     * @return True if this method is called from a legit state. False otherwise
     * @throws IllegalArgumentException when the parameters are invalid or when they are not consistent with the state of
     * the game
     */
    boolean selectCoordinate(Coordinate coordinate, boolean specialPower) throws IllegalArgumentException;

    /**
     *  This method is called when the player decides to skip an optional phase and goes to the next one
     *  @return True if this method is called from a legit state. False otherwise
     * @throws IllegalStateException when it is called from a non-optional state that could have an optional instance
     */
    boolean endPhase() throws IllegalStateException;

    /**
     * This method returns the name of the state
     * @return the enum representing the ID of the state
     */
    Turn.State getStateID();
}
