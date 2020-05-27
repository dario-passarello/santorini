package view.GUI;

import javafx.application.Application;
import model.Player;
import view.View;
import view.screens.Screen;
import view.screens.ScreenFactory;
import view.ViewManager;

import java.util.List;

public class GUIScreenFactory implements ScreenFactory {

    private final ViewManager viewManager;

    public GUIScreenFactory(ViewManager viewManager){
        this.viewManager = viewManager;
    }

    @Override
    public Screen initialize() {
        Screen firstScreen = getGodSelectionScreen("dario");
        GUI.setLaunchController((GUIController) firstScreen);
        Application.launch(GUI.class);
        return firstScreen;
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
        return new GUIGodSelection(viewManager,activePlayer);
    }

    @Override
    public Screen getGodPickScreen(String activePlayer, List<String> godsAvailable) {
        return new GUIPickGod(viewManager,activePlayer,godsAvailable); //Example
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
