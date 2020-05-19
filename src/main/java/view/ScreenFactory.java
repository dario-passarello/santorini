package view;

import java.util.List;

public interface ScreenFactory {

    Screen getMenuScreen(ViewManager client);
    Screen getCreditsScreen(ViewManager client);
    Screen getConnectionScreen(ViewManager client);
    Screen getGodSelectionScreen(ViewManager client, String playerName, List<String> players);
    Screen getGodPickScreen(ViewManager client, String playerName, List<String> players, List<String> godsAvailable);
    Screen getBoardScreen(ViewManager client, String playerName, List<String> players);
    Screen getWinnerScreen(ViewManager client, String winner, List<String> players);

}
