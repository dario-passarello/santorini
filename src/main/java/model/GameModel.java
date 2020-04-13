package model;

import utils.Coordinate;

import java.util.Set;

public interface GameModel {
    /**
     * Sets number of players and then updates the game state
     * @param num Number of players
     * @param hostPlayerName Name of the player who hosts this game
     * @return true if true if this function call is legit for the current GameState function call is legit for the current GameState
     */
    boolean configureGame(String hostPlayerName, int num);
    /**
     * Adds a new player waiting in the lobby and updates the game state
     * @param playerName name of the player
     * @return false if there is another player with the same name of one of the player in the lobby
     * or if the lobby is full.
     */
    boolean registerPlayer(String playerName);
    /**
     * Searches a player by name and removes it from the lobby and then updates the game state
     * @return true if the function call is legit for the current GameState and the player removal succeeded
     */
    boolean unregisterPlayer(String playerName);

    /**
     * Starts the game after all players are in lobby
     * @return true if the are enough players to start the game
     * and if the function call is legit for the current GameState
     */
    boolean readyToStart();

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
     * @param godName
     * @return true if this function call is legit for the current GameState
     */
    boolean pickGod(String godName);
    /**
     * Inputs coordinates in the game state (useful for builders placement phase)
     * @param coordinate The coordinate given to the model
     * @return true if this function call is legit for the current GameState and if
     *  the parameters are valid
     */
    boolean selectCoordinate(Coordinate coordinate);

    /**
     *  Quits the game
     *  @return true if this function call is legit for the current GameState
     */
    boolean quitGame();
}
