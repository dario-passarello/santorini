package view.CLI;

import model.Player;
import view.ViewManager;
import view.screens.BoardScreen;

import java.util.List;

public class CLIBoardScreen extends BoardScreen implements InputProcessor {

    public CLIBoardScreen(ViewManager view, String activePlayer, List<Player> players) {
        super(view, activePlayer, players);
    }

    @Override
    public void processInput(String input) {

    }

    @Override
    public void onScreenOpen() {


        System.out.println("\n TRANSITION DONE: This is the Board Screen: ");
    }

    @Override
    public void onScreenClose() {

    }
}
