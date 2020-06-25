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
        inputHandler.message();

    }


    @Override
    public void processInput(String input) {
        inputHandler.execute(input);
    }

    private void print(String s){
        System.out.print(Colors.RESET + " " + s + DrawElements.inputColor);
    }

    /**
     * This class represents the moment where the player chooses what to do after the end of the game
     */
    class ChooseNext implements InputExecutor{

        @Override
        public void message() {
            print(Colors.YELLOW_227 + "THE GAME HAS ENDED" + Colors.RESET + "\n");
            print("Choose one of the following options: \n");
            print("   (S) - Go to the Menu Screen\n");
            print("   (Q) - Quit the game");

            DrawElements.moveRight(15);
            DrawElements.moveUp(2);
            DrawElements.out.flush();
        }

        @Override
        public void execute(String s) {

            switch(s.toUpperCase()){
                case "S":
                    goToMenuScreen();
                    break;
                case "Q":
                    System.out.print(Colors.RESET);
                    DrawElements.moveDown(3);
                    System.out.print("\n\n");
                    System.exit(0);
                    break;
                default:
                    print("This is an invalid input. Pls Select one of the options ");
            }
        }
    }
}
