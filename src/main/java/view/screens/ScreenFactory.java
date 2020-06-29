package view.screens;

import model.Player;
import model.gods.God;
import utils.Coordinate;

import java.util.List;

/**
 * Factory Interface for building concrete {@link Screen} (factory pattern)
 */
public interface ScreenFactory {
    /**
     *  This method is always called when the run() method of the ViewManager is called
     * @param firstScreen is the first screen that has to be shown
     */
    void initialize(Screen firstScreen);

    /**
     * Creates a new concrete instance of {@link MenuScreen}
     * @return A new instance of MenuScreen
     */
    Screen getMenuScreen();

    /**
     * Creates a new concrete instance of {@link CreditsScreen}
     * @return A new instance of CreditScreen
     */
    Screen getCreditsScreen();

    /**
     * Creates a new concrete instance of {@link ConnectionScreen}
     * @return A new instance of ConnectionScreen
     */
    Screen getConnectionScreen();

    /**
     * Creates a new concrete instance of {@link GodSelectionScreen}
     * @param activePlayer The username of the active player
     * @return A new instance of GodSelectionScreen
     */
    Screen getGodSelectionScreen(String activePlayer);

    /**
     * Creates a new concrete instance of {@link PickGodScreen}
     * @param activePlayer The username of the active player
     * @param godsAvailable A list of gods available to pick
     * @return A new instance of PickGodScreen
     */
    Screen getGodPickScreen(String activePlayer, List<String> godsAvailable);

    /**
     * Creates a new concrete instance of {@link BoardScreen}
     * @param activePlayer The username of the active player
     * @param players A list of all players object representing players in the game
     * @param preHighlightedCoordinates A list of pre highlighted coordinates
     * @return A new instance of BoardScreen
     */
    Screen getBoardScreen(String activePlayer, List<Player> players, List<Coordinate> preHighlightedCoordinates);

    /**
     * Creates a new concrete instance of {@link WinnerScreen}
     * @param players  A list of all players object representing players in the game
     * @return A new instance of WinnerScreen
     */
    Screen getWinnerScreen(List<Player> players);

    /**
     * Creates a new concrete instance of {@link ConnectionErrorScreen}
     * @return A new instance of ConnectionErrorScreen
     */
    Screen getConnectionErrorScreen();

}
