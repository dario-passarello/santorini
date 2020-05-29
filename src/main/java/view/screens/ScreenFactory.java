package view.screens;

import model.Player;

import java.util.List;

public interface ScreenFactory {
    /**
     *  This method is always called when the run() method of the ViewManager is called
     * @param firstScreen
     */
    void initialize(Screen firstScreen);

    Screen getMenuScreen();
    Screen getCreditsScreen();
    Screen getConnectionScreen();
    Screen getGodSelectionScreen(String activePlayer);

    Screen getGodPickScreen(String activePlayer, List<String> godsAvailable);
    Screen getBoardScreen(String activePlayer, List<Player> players);
    Screen getWinnerScreen(String winner);

}
