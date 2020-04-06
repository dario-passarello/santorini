package model.gamestates;

import model.Game;
import model.Player;
import utils.Coordinate;

import java.util.Set;

public class PlaceBuilderState implements GameState {
    Game game;

    public PlaceBuilderState(Game game){
        this.game = game;
    }

    /**
     * Executed when the state machine enters in the state
     */
    public void onEntry() {

    }

    /**
     * Executed when the state machine exits the state
     */
    public void onExit() {

    }

    /**
     * Inputs the number of player to the game
     *
     * @param num number of player participating in the game
     * @param hostPlayerName
     * @return
     */
    public boolean configureGame(int num, String hostPlayerName) {

    }

    /**
     * Adds a player to the game
     *
     * @param name A reference to the player object
     * @return true if the player is correctly added to the game
     */
    public boolean registerPlayer(String name) {
        return false;
    }

    /**
     * Removes a player from the game
     *
     * @param name The player name
     * @return true if the player is correctly removed from the game
     */
    public boolean unregisterPlayer(String name) {
        return false;
    }

    /**
     * Copies a list of god in the game and updates the game state
     *
     * @param godList The list of the names of the gods chosen for the game
     * @return
     */
    public boolean submitGodList(Set<String> godList) {

    }

    /**
     * Inputs coordinates in the game state (useful for builders placement phase)
     *  @param player     The player that is setting the coordinates
     * @param coordinate The coordinate given to the model
     * @return
     */
    public boolean selectCoordinate(Player player, Coordinate coordinate) {

    }

    /**
     * Quits the game
     * @return
     */
    public boolean quitGame() {

    }
}
