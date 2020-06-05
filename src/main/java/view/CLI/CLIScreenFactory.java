package view.CLI;

import model.Player;
import network.Client;
import utils.Coordinate;
import view.GUI.GUIWinner;
import view.screens.Screen;
import view.screens.ScreenFactory;
import view.ViewManager;

import java.util.List;
import java.util.logging.Level;

public class CLIScreenFactory implements ScreenFactory {

    private final ViewManager viewManager;
    private InputListener cliListener;

    public CLIScreenFactory(ViewManager viewManager){
        this.viewManager = viewManager;
    }




    @Override
    public void initialize(Screen firstScreen) {
        Client.logger.setLevel(Level.SEVERE);



        // Set the thread that reads from STDIN
        cliListener = new InputListener();
        cliListener.setScreen((InputProcessor) firstScreen);
        Thread inputReader = new Thread(cliListener);
        inputReader.start();

    }

    @Override
    public Screen getMenuScreen() {
        return new CLIMenuScreen(viewManager);
    }

    @Override
    public Screen getCreditsScreen() {
        return null;
    }

    @Override
    public Screen getConnectionScreen() {
        Screen connectionscreen = new CLIConnectionScreen(viewManager);
        cliListener.setScreen((InputProcessor) connectionscreen);
        return connectionscreen;
    }

    @Override
    public Screen getGodSelectionScreen(String activePlayer) {
        Screen godselectionscreen = new CLIGodSelectionScreen(viewManager, activePlayer);
        cliListener.setScreen((InputProcessor) godselectionscreen);
        return godselectionscreen;
    }

    @Override
    public Screen getGodPickScreen(String activePlayer, List<String> godsAvailable) {
        Screen pickgodscreen = new CLIPickGodScreen(viewManager, activePlayer, godsAvailable);
        cliListener.setScreen((InputProcessor) pickgodscreen);
        return pickgodscreen;
    }

    @Override
    public Screen getBoardScreen(String activePlayer, List<Player> players, List<Coordinate> preHighlightedCoordinates) {
        return null;
    }

    @Override
    public Screen getWinnerScreen(List<Player> players) {
        return null; //new CLIWinner(viewManager, winner);
    }

    @Override
    public Screen getConnectionErrorScreen() {
        return null;
    }


}
