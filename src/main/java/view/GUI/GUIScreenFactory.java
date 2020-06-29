package view.GUI;

import javafx.application.Application;
import model.Player;
import model.gods.God;
import utils.Coordinate;
import view.screens.Screen;
import view.screens.ScreenFactory;
import view.ViewManager;

import java.util.List;

/**
 * A factory for GUIScreen (factory pattern)
 */
public class GUIScreenFactory implements ScreenFactory {

    private final ViewManager viewManager;

    /**
     * GUIScreenFactory constructor
     * @param viewManager the view manager used
     */
    public GUIScreenFactory(ViewManager viewManager){
        this.viewManager = viewManager;
    }

    @Override
    public void initialize(Screen firstScreen) {
        GUI.setLaunchController((GUIController) firstScreen);
        Application.launch(GUI.class);

    }

    @Override
    public Screen getMenuScreen() {
        return new GUIMenu(viewManager);
    }

    @Override
    public Screen getCreditsScreen() {
        return new GUICredits(viewManager);
    }

    @Override
    public Screen getConnectionScreen() {
        return new GUIConnection(viewManager);
    }

    @Override
    public Screen getGodSelectionScreen(String activePlayer) {
        return new GUIGodSelection(viewManager,activePlayer);
    }

    @Override
    public Screen getGodPickScreen(String activePlayer, List<String> godsAvailable) {
        return new GUIPickGod(viewManager,activePlayer,godsAvailable); //Example
    }

    @Override
    public Screen getBoardScreen(String activePlayer, List<Player> players, List<Coordinate> preHighlightedCoordinates) {
        return new GUIBoard(viewManager,activePlayer,players,preHighlightedCoordinates);
    }

    @Override
    public Screen getWinnerScreen(List<Player> players) {
        return new GUIWinner(viewManager, players);
    }

    @Override
    public Screen getConnectionErrorScreen() {
        return new GUIConnectionError(viewManager);
    }


}
