package view.CLI;

import view.ViewManager;
import view.screens.WinnerScreen;

public class CLIWinnerScreen extends WinnerScreen {

    @Override
    public void onScreenOpen(){
        System.out.println("Good Job boys, this is the end of the game");
        System.out.println("The real winner is whoever had fun");
    }

    public CLIWinnerScreen(ViewManager view) {
        super(view);
    }
}
