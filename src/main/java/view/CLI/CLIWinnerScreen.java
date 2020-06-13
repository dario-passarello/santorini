package view.CLI;

import model.Player;
import view.ViewManager;
import view.screens.WinnerScreen;

import java.util.List;

public class CLIWinnerScreen extends WinnerScreen implements InputProcessor {

    private List<Player> players;
    InputExecutor inputHandler;

    public CLIWinnerScreen(ViewManager view, List<Player> players) {
        super(view);
        this.players = players;
    }

    @Override
    public void onScreenOpen(){

        DrawElements.writeWinner(players, getWinner(players), getThisPlayerName());
        inputHandler = new ChooseNext();

    }


    @Override
    public void processInput(String input) {
        inputHandler.execute(input);
    }

    /**
     * This class represents the moment where the player chooses what to do after the end of the game
     */
    class ChooseNext implements InputExecutor{

        @Override
        public void message() {

        }

        @Override
        public void execute(String s) {

        }
    }
}
