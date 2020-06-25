package view.CLI;

import view.ViewManager;
import view.screens.MenuScreen;

public class CLIMenuScreen extends MenuScreen implements InputProcessor {

    InputExecutor expectedInput;

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
    public class Start implements InputExecutor{

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

    public class Credits implements InputExecutor{

        @Override
        public void message() {
            System.out.print(DrawElements.FLUSH);
            print("Credits Screen: \n");
            print("There is nothing yet. Tybe B to go back: ");
        }

        @Override
        public void execute(String s) {
            if(s.toUpperCase().equals("B")) onScreenOpen();
            else print("This is not a valid input. Pls try again ");
        }
    }


}
