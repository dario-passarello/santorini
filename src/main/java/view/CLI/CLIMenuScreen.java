package view.CLI;

import view.ViewManager;
import view.screens.MenuScreen;

/**
 * The screen that shows up at the start of the application
 */
public class CLIMenuScreen extends MenuScreen implements InputProcessor {

    InputExecutor expectedInput;

    /**
     * Standard constructor
     * @param view The client caller
     */
    public CLIMenuScreen(ViewManager view) {
        super(view);
    }

    @Override
    public void onScreenOpen() {

        System.out.println(DrawElements.FLUSH);
        DrawElements.drawTitle(Colors.WHITE_231, Colors.BLUE_27);
        DrawElements.drawStartGameBox();
        DrawElements.drawCreditsGameBox();
        DrawElements.drawQuitGameBox();

        expectedInput = new Start();
        expectedInput.message();



    }

    @Override
    public void onScreenClose() {
        System.out.println(DrawElements.FLUSH);
    }

    private void print(String s){
        System.out.print(Colors.RESET + " " + s + DrawElements.inputColor);
    }

    @Override
    public void processInput(String input) {
        expectedInput.execute(input);
    }



    //      +-------------------+
    //      +   INNER CLASSES   +
    //      +-------------------+

    /**
     * This class represents the beginning state of the game, with the Title and the choice boxes
     */
    class Start implements InputExecutor{

        @Override
        public void message() { print("\n Select one of the options: ");
        }

        @Override
        public void execute(String s) {

            switch(s.toUpperCase()){
                case "S":
                    goToConnectionScreen();
                    break;
                case "C":
                    expectedInput = new Credits();
                    expectedInput.message();
                    break;
                case "Q":
                    System.out.print(Colors.RESET + " QUITTING THE GAME.... \n");
                    System.exit(0);
                    break;
                default:
                    print("The command is invalid or not implemented yet ");
            }
        }
    }

    /**
     * This class represents the credits screen
     */
    class Credits implements InputExecutor{
        @Override
        public void message() {
            System.out.print(DrawElements.FLUSH);
            DrawElements.DrawMassimoCredit();
            DrawElements.DrawDarioCredit();
            DrawElements.DrawAnisCredit();

            print(Colors.GREEN_47 + "THANK YOU FOR PLAYING!\n");
            print("Tybe B to go back: ");
        }

        @Override
        public void execute(String s) {
            if(s.toUpperCase().equals("B")) onScreenOpen();
            else print("This is not a valid input. Pls try again ");
        }
    }


}
