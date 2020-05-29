package view.CLI;

import view.ViewManager;
import view.screens.MenuScreen;

public class CLIMenuScreen extends MenuScreen implements InputProcessor {


    public CLIMenuScreen(ViewManager view) {
        super(view);
    }

    @Override
    public void onScreenOpen() {

        //TODO
        goToConnectionScreen();
    }

    @Override
    public void onScreenClose() {

    }

    @Override
    public void processInput(String input) {

    }
}
