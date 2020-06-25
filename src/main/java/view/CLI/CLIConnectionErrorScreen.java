package view.CLI;


import view.ViewManager;
import view.screens.ConnectionErrorScreen;

public class CLIConnectionErrorScreen extends ConnectionErrorScreen implements InputProcessor {

    private boolean fromBoard;
    private InputExecutor nextScreen;
    private String errorMessage = "SOMETHING WENT WRONG... CONNECTION TO THE SERVER TIMED OUT...";

    public CLIConnectionErrorScreen(ViewManager view, boolean fromBoard) {
        super(view);
        this.fromBoard = fromBoard;
    }

    @Override
    public void onScreenOpen() {
        if(fromBoard){
            DrawElements.saveCursor();
            DrawElements.writeConnectionError(errorMessage);
            DrawElements.restoreCursor();
        }
        else System.out.print(DrawElements.errorMessageColor + "\n\n " + errorMessage + "\n" + Colors.RESET);

        nextScreen = new Next();
        nextScreen.message();
    }

    @Override
    public void processInput(String input) {
        nextScreen.execute(input);
    }


    //      +-------------------+
    //      +   INNER CLASSES   +
    //      +-------------------+

    /**
     * This class represents the moment where the player has to choose what to do next
     */
    public class Next implements InputExecutor{

        @Override
        public void message() {
            if(fromBoard) print(Colors.YELLOW_227 + "DISCONNECTED" + Colors.RESET + "\n");
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
                    close();
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


    private void print(String toPrint){
        System.out.print(Colors.RESET + " " + toPrint + DrawElements.inputColor);
    }
}
