package view.CLI;

import model.Player;
import view.screens.Screen;
import view.screens.ScreenFactory;
import view.ViewManager;

import java.util.List;

public class CLIScreenFactory implements ScreenFactory {

    private final ViewManager viewManager;

    public CLIScreenFactory(ViewManager viewManager){
        this.viewManager = viewManager;
    }


    @Override
    public Screen getMenuScreen() {
        return null;
    }

    @Override
    public Screen getCreditsScreen() {
        return null;
    }

    @Override
    public Screen getConnectionScreen() {
        return null;
    }

    @Override
    public Screen getGodSelectionScreen(String activePlayer) {
        return null;
    }

    @Override
    public Screen getGodPickScreen(String activePlayer, List<String> godsAvailable) {
        return null;
    }

    @Override
    public Screen getBoardScreen(String activePlayer, List<Player> players) {
        return null;
    }

    @Override
    public Screen getWinnerScreen(String winner) {
        return null;
    }


}
