package model.gamestates;

import model.Player;
import utils.Coordinate;

import java.util.Set;

/**
 *  Interface for the States of the GameState machine
 */
public interface GameState {
    /**
     *  Executed when the state machine enters in the state
     */
    void onEntry();
    /**
     *  Executed when the state machine exits the state
     */
    void onExit();

    /**
     *  Inputs the number of player to the game
     * @param num number of player participating in the game
     * @param hostPlayerName the player name of the host
     * @return
     */
    boolean configureGame(int num, String hostPlayerName);

    /**
     * Adds a player to the game
     * @param name A reference to the player object
     * @return true if the player is correctly added to the game
     */
    boolean registerPlayer(String name);
    /**
     * Removes a player from the game
     * @param name The player name
     * @return true if the player is correctly removed from the game
     */
    boolean unregisterPlayer(String name);

    boolean readyToStart();

    /**
     * Copies a list of god in the game and updates the game state
     * @param godList The list of the names of the gods chosen for the game
     * @return
     */
    boolean submitGodList(Set<String> godList);
    /**
     * Inputs coordinates in the game state (useful for builders placement phase)
     * @param player The player that is setting the coordinates
     * @param coordinate The coordinate given to the model
     * @return
     */
    boolean selectCoordinate(Player player, Coordinate coordinate);
    /**
     *  Quits the game
     * @return
     */
    boolean quitGame();

}
