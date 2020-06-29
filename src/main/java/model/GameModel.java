package model;

import utils.Coordinate;

import java.util.List;
import java.util.Set;

/**
 * This is the interfaces that defines the game as the target for the methods of the Game State Machine
 */
public interface GameModel {
    /**
     * Receives the list of the names of the gods chosen by the host for the game
     * @param godList The list of the names of the gods chosen for the game, it should contain
     *                a number of valid god names equal to the number of the player in the game or
     *                it could be left empty (in this case a game with 3 "mortals" will be initialized
     * @return true if this function call is legit for the current GameState and if godList
     *  is a valid list
     */
    boolean submitGodList(Set<String> godList);

    /**
     * Receive the godPick from the player
     * @param playerName the name of the player that chooses the god
     * @param godName the name of the god chosen
     * @return true if this function call is legit for the current GameState
     */
    boolean pickGod(String playerName, String godName);

    /**
     * Inputs coordinates in the game state (useful for builders placement phase)
     * @param playerName The name of the player that selects the coordinate
     * @param coordinate The coordinate given to the model
     * @return true if this function call is legit for the current GameState and if
     *  the parameters are valid
     */
    boolean selectCoordinate(String playerName, Coordinate coordinate);

    /**
     *  Quits the game
     *  @param playerName Name of the player
     *  @return true if this function call is legit for the current GameState
     */
    boolean quitGame(String playerName);


    /**
     * @return the state identifier of the current GameState
     */
    Game.State getStateIdentifier();
}
