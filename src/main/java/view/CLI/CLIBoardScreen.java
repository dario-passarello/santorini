package view.CLI;

import model.Player;
import utils.Coordinate;
import view.ViewManager;
import view.screens.BoardScreen;

import java.util.List;

public class CLIBoardScreen extends BoardScreen implements InputProcessor {

    public CLIBoardScreen(ViewManager view, String activePlayer, List<Player> players, List<Coordinate> preHighCoords) {
        super(view, activePlayer, players, preHighCoords);
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
