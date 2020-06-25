package view.CLI;

import view.*;
import view.screens.GodSelectionScreen;

import java.util.List;


public class CLIGodSelectionScreen extends GodSelectionScreen implements InputProcessor {

    private String selectedGod;
    private InputExecutor expectedInput;

    public CLIGodSelectionScreen(ViewManager viewManager, String activePlayer){
        super(viewManager, activePlayer);
        this.selectedGod = null;
    }



    @Override
    public void onScreenOpen() {

        expectedInput = new GodSelection();

        refreshMainScreen();
        expectedInput.message();

    }

    @Override
    public void onScreenClose() {

    }

    @Override
    public void processInput(String input) {

        expectedInput.execute(input);
    }

    /**
     * This method draws the box with the set of gods inside
     */
    private void printMainScreen(){
        System.out.println(Colors.BLUE_27 + "\n\n            GOD SELECTION PHASE" + Colors.RESET);

        int i = 0;
        GodAssetsBundle god;
        try{
            System.out.println("╔═════════════════════════════════════════╗");
            while(true){
                god = AssetLoader.getGodAssetsBundle(i);
                String first = "(" + i + ")" + " - " + god.getName();
                System.out.printf("║ %-20s", first);
                i++;
                god = AssetLoader.getGodAssetsBundle(i);
                String second = "(" + i + ")" + " - " + god.getName();
                System.out.printf("%-20s║\n", second);
                i++;
            }
        }
        catch(IllegalArgumentException exception){
            if(i%2 == 1){
                String empty = "";
                System.out.printf("%-20s║\n", empty);
            }
            System.out.println("╚═════════════════════════════════════════╝");


        }
    }

    /**
     * This method refreshes the screen, drawing the board again
     */
    private void refreshMainScreen(){
        DrawElements.out.println(DrawElements.FLUSH);
        printMainScreen();
        for(String god : getChosenGodList()){
            selectGod(AssetLoader.getGodAssetsBundle(god).getId(), true);
        }
    }

    /**
     * This method highlights a god in the box as selected
     * @param i The number of the god
     * @param confirm The boolean that specifies if the god is only to highlight or to mark as selected
     */
    private void selectGod(int i, boolean confirm){

        // Get the cursor to the starting of the table
        System.out.print(DrawElements.ESC + "H" + DrawElements.ESC + "1B");
        DrawElements.moveDown(4);
        DrawElements.moveRight(1);
        DrawElements.out.flush();

        // Get the cursor to the correct God
        if(i%2 == 0 && i != 0){
            DrawElements.moveDown(i/2);
            DrawElements.out.flush();

        }
        else if(i != 0){
            DrawElements.moveRight(20);
            if(i-1 != 0) DrawElements.moveDown((i-1)/2);
            DrawElements.out.flush();
        }

        // Assign the correct colors
        String color;
        String background;
        if(!confirm) {
            if(getChosenGodList().contains(AssetLoader.getGodNameFromID(i))) color = Colors.GREEN_47;
            else {
                color = Colors.BLACK_232;
            }
            background = Colors.WHITEBG_231;
        }
        else{
            color = Colors.GREEN_47;         // GREEN COLOR
            background = "";
        }

        GodAssetsBundle god = AssetLoader.getGodAssetsBundle(i);

        // Number of spaces to write to fill the column
        int spaces = 20 - 6 - (Integer.toString(i).concat(god.getName())).length();
        // Writing the god in an highlighted way
        String print = " (" + i + ")" + " - " + god.getName();
        System.out.print(color + background + print);
        for(int j = 0; j < spaces; j++){
            System.out.print(" ");
        }
        System.out.print(Colors.RESET);
        System.out.print(DrawElements.ESC + "15H");
    }


    private void print(String s){
        System.out.print(Colors.RESET + " " + s + DrawElements.inputColor);
    }



    //      +-------------------+
    //      +   INNER CLASSES   +
    //      +-------------------+

    /**
     * This is the class starting phase of the God Selection, where you select the god and navigate through their descriptions
     */
    class GodSelection implements InputExecutor{

        @Override
        public void message(){
            if(activeScreen) print(
                    "Select the God Powers you want to be present in this match \n" +
                    " Select the God by typing the corresponding Number \n" +
                    " Once selected, press Enter to confirm the Selection: ");
            else{
                print("Waiting for the designated player to select the gods for this match... \n" +
                        " You can check the information about the gods by simply entering their number: ");
            }
        }

        @Override
        public void execute(String s) {
            try{
                int id = Integer.parseInt(s);
                String inputGod = AssetLoader.getGodNameFromID(id);

                if(isGodChosen(inputGod)){  // If the god you have chosen has already been selected
                    selectedGod = inputGod;
                    refreshMainScreen();
                    selectGod(id, false);
                    System.out.println(Colors.BLUE_153 + AssetLoader.getGodAssetsBundle(id).getDescription() + Colors.RESET);
                    expectedInput = new Deselection();
                    expectedInput.message();
                }
                else {
                    if(!activeScreen){  // If you're not the active screen, you can only see the Description
                        refreshMainScreen();
                        selectGod(id, false);
                        System.out.println(Colors.BLUE_153 + AssetLoader.getGodAssetsBundle(id).getDescription() + Colors.RESET);
                        return;
                    }
                    selectedGod = AssetLoader.getGodNameFromID(id);
                    refreshMainScreen();
                    selectGod(id, false);
                    System.out.println(Colors.BLUE_153 + AssetLoader.getGodAssetsBundle(id).getDescription() + Colors.RESET);
                    expectedInput = new ConfirmSelection();
                    expectedInput.message();
                }

            }
            catch(NumberFormatException exception){
                print("This is not a valid Input. Please Try again: ");
            }
            catch(IllegalArgumentException exception){
                print(exception.getMessage() + ". Please Try again: ");
            }
        }
    }

    /**
     * This class represents the phase where the player either confirms the selection or returns to the choosing screen
     */
    class ConfirmSelection implements InputExecutor{

        @Override
        public void message(){
            print( "\n Press Enter to confirm the selection or " +
                    "select another God: ");
        }

        @Override
        public void execute(String s) {
            try {
                if (s.equals("") && selectedGod != null) {  // If the use pressed Enter
                    // Check that the selected god is not a Mortal with a non-empty list of gods selected
                    if(selectedGod.equals("Mortal") && getChosenGodList().size() > 0){
                        throw new IllegalActionException("You can select the mortal only when there are no gods selected ");
                    }
                    addGod(selectedGod);
                    int id = AssetLoader.getGodAssetsBundle(selectedGod).getId();
                    System.out.println("\n " + Colors.BLUE_153 + selectedGod + Colors.RESET + " Has been Selected ");
                    selectedGod = null;
                    // If the Confirm adds the last god
                    if(readyToSubmit()){
                        List<String> selected = getChosenGodList();
                        refreshMainScreen();
                        System.out.println("\n These are the gods selected:");
                        for(String god : selected){
                            System.out.println(" (" + AssetLoader.getGodAssetsBundle(god).getId() + ") - " + Colors.YELLOW_227 +
                                    god + Colors.RESET);
                        }
                        expectedInput = new SubmitList();
                        expectedInput.message();
                        return;
                    }
                    refreshMainScreen();
                    expectedInput = new GodSelection();
                    print("\n You can select another god:  ");
                }
                else {
                    new GodSelection().execute(s);
                }
            }
            catch(IllegalActionException exception){
                print(exception.getMessage() + ". Pls try again");
            }
            catch(IllegalValueException exception){

            }
        }
    }

    /**
     * This class represents the moment where the god selected is already been chosen and it gives the player
     * the possiblity to undo the selection
     */
    class Deselection implements  InputExecutor{

        @Override
        public void message(){
            print( "\n This GOD Has already been selected. Press D to deselect it or" +
                    "select another god: ");
        }
        @Override
        public void execute(String s) {
            try {
                if (s.toUpperCase().equals("D")) {
                    removeGod(selectedGod);
                    refreshMainScreen();
                    selectGod(AssetLoader.getGodAssetsBundle(selectedGod).getId(), false);
                    expectedInput = new ConfirmSelection();
                    expectedInput.message();
                }
                else{
                    new GodSelection().execute(s);
                }
            }
            catch(IllegalActionException exception){
                print(exception.getMessage() + ". Pls try again  ");
            }
            catch(IllegalValueException exception){
                print(exception.getMessage() + ". Pls try again  ");
            }
        }
    }

    /**
     * This class represents the moment where the player submit the list of gods selected or chooses to remove one
     * and continue the selection
     */
    class SubmitList implements InputExecutor{

        @Override
        public void message(){
            print("\n Press Enter to confirm the selection or type a number to delete\n" +
                    " a god from the list:  ");
        }
        @Override
        public void execute(String s) {
                if (s.equals("")) {     // If the player presses enter, he confirms the god list
                    try {
                        submitGodList();
                    } catch (IllegalActionException exception) {

                    }
                }
                else{                   // If not, we check that he entered a number from the list to deselect
                    try{
                        int number = Integer.parseInt(s);
                        if(getChosenGodList().contains(AssetLoader.getGodNameFromID(number))){
                            removeGod(AssetLoader.getGodNameFromID(number));
                            refreshMainScreen();
                            print("The god has been deselected. Pls choose a god:  ");
                            expectedInput = new GodSelection();
                        }
                        else{           // If it was neither of the two, the player will be notified accordingly
                            print("The number is not in the list. Pls select another number:  ");
                        }
                    }
                    catch(NumberFormatException exception){
                        print("This is not a number. Pls Enter a valid input:  ");
                    }
                    catch(IllegalValueException exception){
                        print(exception.getMessage() + ". Pls try again  ");
                    }
                    catch(IllegalActionException exception){
                        print(exception.getMessage() + ". Pls try again  ");
                    }
                }


        }
    }
}
