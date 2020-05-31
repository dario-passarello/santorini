package view.CLI;

import view.IllegalValueException;
import view.ViewManager;
import view.screens.PickGodScreen;

import java.util.List;

public class CLIPickGodScreen extends PickGodScreen implements InputProcessor {

    private InputExecutor expectedInput;

    public CLIPickGodScreen(ViewManager view, String firstActivePlayer, List<String> availableGods) {
        super(view, firstActivePlayer, availableGods);
    }

    @Override
    public void processInput(String input) {
        expectedInput.execute(input);
    }

    @Override
    public void onScreenOpen() {

            // TEMPORARY VISUAL
            System.out.println("TRANSITION DONE:  This is the GodPickScreen\n");
            expectedInput = new GodPIckChoice();

            int i = 1;
            for(String god : getAllGodToChoose()){
                System.out.println("(" + i + ")" + " - " + god );
                i++;
            }

            if(activeScreen){
                System.out.println("You can choose one of the following gods");
            }
            else{
                System.out.println("One of the players is choosing his god. Pls wait until it is your turn to pick");
            }

    }

    @Override
    public void onScreenClose() {

    }


    //      +-------------------+
    //      +   INNER CLASSES   +
    //      +-------------------+


    class GodPIckChoice implements InputExecutor{

        @Override
        public void execute(String s) {
            try{
                int selected = Integer.parseInt(s);
                if(selected!= 0 && selected < getGodsRemaining().size()){
                    pickGod(getGodsRemaining().get(selected-1));
                }
                else{
                    System.out.print("The number is not on the list: Please enter another number");
                }
            }
            catch(NumberFormatException exception){
                System.out.print("\nThis is not a number. Please enter a number: ");
            }
            catch(IllegalValueException exception){

            }
        }
    }
}
