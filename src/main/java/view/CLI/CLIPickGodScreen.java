package view.CLI;

import model.Game;
import view.AssetLoader;
import view.IllegalValueException;
import view.ViewManager;
import view.screens.PickGodScreen;

import java.util.List;

public class CLIPickGodScreen extends PickGodScreen implements InputProcessor {

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

    /**
     * This method draws the box that contains the gods, along with their description
     */
    private void MainScreen(){
        System.out.print(Colors.RESET);
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
        // If we are still in the godPick state, update everyone's screen
        if(getNextState() == Game.State.GOD_PICK){
            System.out.println(DrawElements.FLUSH);
            MainScreen();
            expectedInput.message();
        }
    }

    private void print(String s){
        System.out.print(Colors.RESET + " " + s + DrawElements.inputColor);
    }

    //      +-------------------+
    //      +   INNER CLASSES   +
    //      +-------------------+


    /**
     * This class represent the god Picking phase
     * It is the only inner class of the class since the GodPick consists of
     * a single input from the user
     */
    class GodPIckChoice implements InputExecutor{

        @Override
        public void message(){
            if(activeScreen){
                print("You can choose one of the following gods ");
            }
            else{
                print("One of the players is choosing his god. Pls wait... ");
            }
        }

        @Override
        public void execute(String s) {
            try{
                // Parse the input and sends the corresponding god to the super screen
                int selected = Integer.parseInt(s);
                if(selected != 0 && selected <= getAllGodToChoose().size()){
                    if(getGodsRemaining().contains(getAllGodToChoose().get(selected - 1)))
                        pickGod(getAllGodToChoose().get(selected-1));
                    else{
                        print("This god has already been selected: Please enter another number ");
                    }
                }
                else{
                    print("This number is not in the list: Please enter another number ");
                }
            }
            catch(NumberFormatException exception){
                print("This is not a number. Please enter a number: ");
            }
            catch(IllegalValueException exception){
                print(exception.getMessage() + ". Pls try again ");
            }
        }
    }
}
