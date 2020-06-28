package model.gamestates;

import model.Game;
import utils.Coordinate;

import java.util.Set;

/**
 *  Interface for the States of the GameState machine
 */
public interface GameState {

    /**
     * This methods creates the instances of all the gods selected and sends the update to all the clients
     * @param godNamesList The names of the gods selected by the player
     * @return True if this method is called from the correct state. False otherwise
     * @throws IllegalArgumentException when the list does not have the correct structure
     */
    boolean submitGodList(Set<String> godNamesList) throws IllegalArgumentException;

    /**
     * This method links the selected got to the current player
     * @param playerName The name of the player caller
     * @param godName The name of the god selected
     * @return True if this method is called from the correct state. False otherwise
     * @throws IllegalArgumentException when the name of the god is Invalid or when the player who calls this method is
     * not the current player
     */
    boolean pickGod(String playerName, String godName) throws IllegalArgumentException;

    /**
     * This method creates a builder on the board at the given coordinate
     * @param playerName The name of the player caller
     * @param coordinate The coordinate where the builder will be placed
     * @return True if this method is called from the correct state. False otherwise
     * @throws IllegalArgumentException when the Coordinate is invalid (either not in the board or already occupied) or
     * when the Player who calls this method is not the current player
     */
    boolean selectCoordinate(String playerName, Coordinate coordinate) throws IllegalArgumentException;

    /**
     * This method instantly transitions the game to the End Game State
     * @param playerName The player calling the method
     * @return True if this method is called from the correct state. False otherwise
     * @throws IllegalArgumentException when the player caller is not in the game
     */
    boolean quitGame(String playerName) throws IllegalArgumentException;

    /**
     * This method gets the unique identifier of this state
     * @return The enum identifier of the state
     */
    Game.State getStateIdentifier();

}
