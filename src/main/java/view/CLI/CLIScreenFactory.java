package view.CLI;

import model.Player;
import network.Client;
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
    public Screen initialize() {
        Client.logger.setLevel(Level.SEVERE);

        Screen startingScreen = getConnectionScreen();

        // Set the thread that reads from STDIN
        cliListener = new InputListener();
        cliListener.setScreen((InputProcessor) startingScreen);
        Thread inputReader = new Thread(cliListener);
        inputReader.start();

        return startingScreen;
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
        return new CLIConnectionScreen(viewManager);
    }

    @Override
    public Screen getGodSelectionScreen(String activePlayer) {
        Screen godselectionscreen = new CLIGodSelectionScreen(viewManager, activePlayer);
        cliListener.setScreen((InputProcessor) godselectionscreen);
        return godselectionscreen;
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
