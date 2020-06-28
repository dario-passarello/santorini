package view.screens;

import model.Player;
import model.gods.God;
import utils.Coordinate;

import java.util.List;

/**
 * a factory for {@link Screen} (factory pattern)
 */
public interface ScreenFactory {
    /**
     *  This method is always called when the run() method of the ViewManager is called
     * @param firstScreen is the first screen that has to be shown
     */
    void initialize(Screen firstScreen);

    Screen getMenuScreen();
    Screen getCreditsScreen();
    Screen getConnectionScreen();
    Screen getGodSelectionScreen(String activePlayer);

    Screen getGodPickScreen(String activePlayer, List<String> godsAvailable);
    Screen getBoardScreen(String activePlayer, List<Player> players, List<Coordinate> preHighlightedCoordinates);
    Screen getWinnerScreen(List<Player> players);
    Screen getConnectionErrorScreen();

}
