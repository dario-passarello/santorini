package model.gamestates;

import model.Player;
import model.gods.God;
import utils.Coordinate;

import java.util.List;

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
     *  @param num number of player participating in the game
     */
    void setNumberOfPlayers(int num);

    /**
     * Adds a player to the game
     * @param p A reference to the player object
     * @return true if the player is correctly added to the game
     */
    boolean addPlayer(Player p);
    /**
     * Removes a player from the game
     * @param p The player name
     * @return true if the player is correctly removed from the game
     */
    boolean removePlayer(String p);

    /**
     * Copies a list of god in the game and updates the game state
     * @param godList The list of the names of the gods chosen for the game
     */
    void submitGodList(List<String> godList);
    /**
     * Inputs coordinates in the game state (useful for builders placement phase)
     * @param player The player that is setting the coordinates
     * @param coordinate The coordinate given to the model
     */
    void selectCoordinate(Player player, Coordinate coordinate);
    /**
     *  Quits the game
     */
    void quitGame();

}
