package view.CLI;

import model.Game;
import view.AssetLoader;
import view.IllegalValueException;
import view.ViewManager;
import view.screens.PickGodScreen;

import java.util.List;

public class CLIPickGodScreen extends PickGodScreen implements InputProcessor {

    //TODO Find a way to know who selected the gods
    // Added Next State Getter
    private InputExecutor expectedInput;

    private final String player1Color = Colors.BLUE_21;
    private final String player2Color = Colors.YELLOW_226;
    private final String player3Color = Colors.RED_196;

    public CLIPickGodScreen(ViewManager view, String firstActivePlayer, List<String> availableGods) {
        super(view, firstActivePlayer, availableGods);
    }

    @Override
    public void processInput(String input) {
        if(activeScreen) expectedInput.execute(input);
    }

    @Override
    public void onScreenOpen() {


            System.out.println(DrawElements.FLUSH);
            System.out.println(Colors.BLUE_27 + "\n\n            GOD PICKING PHASE" + Colors.RESET);
            expectedInput = new GodPIckChoice();

            MainScreen();
            expectedInput.message();




    }

    @Override
    public void onScreenClose() {

    }

    private void MainScreen(){
        String taken;

        // Print the list of gods to choose
        System.out.println("╔══");
        int i = 1;
        for(String god : getAllGodToChoose()){
            if(!getGodsRemaining().contains(god)) taken = " - TAKEN";
            else taken = "";
            System.out.println("║ (" + i + ") " + Colors.YELLOW_227 + god + Colors.RED_196 + taken + Colors.RESET );
            i++;
        }
        System.out.println("╚══\n");

        // Printing the Descriptions of the gods
        for(String god : getAllGodToChoose()){
            System.out.println(" - " + Colors.YELLOW_227 + god + Colors.RESET);
            System.out.println(Colors.YELLOW_229 + AssetLoader.getGodAssetsBundle(god).getDescription() + Colors.RESET + "\n");
        }


    }

    @Override
    public synchronized void receiveUpdateDone(){
        super.receiveUpdateDone();
        if(getNextState() == Game.State.GOD_PICK){
            System.out.println(DrawElements.FLUSH);
            MainScreen();
        }
    }


    //      +-------------------+
    //      +   INNER CLASSES   +
    //      +-------------------+


    class GodPIckChoice implements InputExecutor{

        @Override
        public void message(){
            if(activeScreen){
                System.out.println("You can choose one of the following gods");
            }
            else{
                System.out.println("One of the players is choosing his god. Pls wait...");
            }
        }

        @Override
        public void execute(String s) {
            try{
                int selected = Integer.parseInt(s);
                if(selected != 0 && selected <= getGodsRemaining().size()){
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
