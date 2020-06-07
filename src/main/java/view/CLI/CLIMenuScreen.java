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

    @Override
    public void processInput(String input) {
        expectedInput.execute(input);
    }

    public class Start implements InputExecutor{

        @Override
        public void message() {
            System.out.print("\nSelect one of the options: ");
        }

        @Override
        public void execute(String s) {
            if(s.toUpperCase().equals("S")){
                goToConnectionScreen();
            }
            else{
                System.out.print("The command is invalid or not implemented yet ");
            }
        }
    }
}
