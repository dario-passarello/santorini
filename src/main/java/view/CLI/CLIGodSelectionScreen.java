package view.CLI;

import view.*;
import view.screens.GodSelectionScreen;

import java.util.List;

//TODO Adjust text order
//TODO Add Sentence when a god is Selected
//TODO Increase readibility of Sentences

//TODO IMPORTANT: Send confirmation message when all the Gods are Selected

public class CLIGodSelectionScreen extends GodSelectionScreen implements InputProcessor {

    public CLIGodSelectionScreen(ViewManager viewManager, String activePlayer){
        super(viewManager, activePlayer);
        this.selectedGod = null;
    }

    private String selectedGod;
    private InputExecutor expectedInput;

    @Override
    public void onScreenOpen() {

        expectedInput = new GodSelection();

        //TEMPORARY VISUAL


        System.out.println("\n\nGOD SELECTION PHASE");

        int i = 0;
        GodAssetsBundle god;
        try{
            while(true){
                god = AssetLoader.getGodAssetsBundle(i);
                String first = "(" + i + ")" + " - " + god.getName();
                System.out.printf("%-20s", first);
                i++;
                god = AssetLoader.getGodAssetsBundle(i);
                String second = "(" + i + ")" + " - " + god.getName();
                System.out.printf("%-20s\n", second);
                i++;

            }
        }
        catch(IllegalArgumentException exception){
            // The end of File. Do nothing
        }

        if(activeScreen) System.out.print(   "\nSelect The Gods you want to play with in this match\n" +
                            "Select the God by typing the corresponding Number: \n" +
                            "Once You selected a God, press Enter to confirm the Selection:     \n\n");
        else{
            System.out.print("\nWaiting for the designated player to select the gods for this match... \n" +
                                "You can check the information about the gods by simply entering their number: \n");
        }

    }

    @Override
    public void onScreenClose() {

    }

    @Override
    public void processInput(String input) {

        expectedInput.execute(input);
    }




    //      +-------------------+
    //      +   INNER CLASSES   +
    //      +-------------------+

    class GodSelection implements InputExecutor{

        @Override
        public void execute(String s) {
            try{
                int id = Integer.parseInt(s);
                String inputGod = AssetLoader.getGodNameFromID(id);

                if(isGodChosen(inputGod)){
                    selectedGod = inputGod;
                    System.out.println("\n" + AssetLoader.getGodAssetsBundle(id).getDescription());
                    System.out.println( "\nThis GOD Has already been selected. Press D to deselect it or" +
                                        "select another god");
                    expectedInput = new Deselection();
                }
                else {
                    if(!activeScreen){
                        System.out.println("\n" + AssetLoader.getGodAssetsBundle(id).getDescription());
                        return;
                    }
                    selectedGod = AssetLoader.getGodNameFromID(id);
                    System.out.println("\n" + AssetLoader.getGodAssetsBundle(id).getDescription());
                    System.out.println("\nPress Enter to confirm the selection or" +
                                        "select another God:\t");
                    expectedInput = new ConfirmSelection();
                }

            }
            catch(NumberFormatException exception){
                System.out.println("This is not a valid Input. Please Try again: ");
            }
            catch(IllegalArgumentException exception){
                System.out.println(exception.getMessage() + ". Please Try again: ");
            }
        }
    }

    class ConfirmSelection implements InputExecutor{

        @Override
        public void execute(String s) {
            try {
                if (s.equals("") && selectedGod != null) {
                    addGod(selectedGod);

                    System.out.println("\n " + selectedGod + " Has been Selected");
                    selectedGod = null;
                    // If the Confirm adds the last god
                    if(readyToSubmit()){
                        List<String> selected = getChosenGodList();
                        System.out.println("\nThese are the gods selected:");
                        for(String god : selected){
                            System.out.println("(" + AssetLoader.getGodAssetsBundle(god).getId() + ") - " + god);
                        }
                        System.out.println("\nPress Enter to confirm the selection or choose a number to delete\n" +
                                "a god from the list:\t");
                        expectedInput = new SubmitList();
                        return;
                    }
                    expectedInput = new GodSelection();
                }
                else {
                    new GodSelection().execute(s);
                }
            }
            catch(IllegalActionException exception){

            }
            catch(IllegalValueException exception){

            }
        }
    }

    class Deselection implements  InputExecutor{

        @Override
        public void execute(String s) {
            try {
                if (s.toUpperCase().equals("D")) {
                    removeGod(selectedGod);
                    expectedInput = new ConfirmSelection();
                    System.out.println("\nPress Enter to confirm the selection or" +
                            "select another God:\t");
                }
                else{
                    new GodSelection().execute(s);
                }
            }
            catch(IllegalActionException exception){

            }
            catch(IllegalValueException exception){

            }
        }
    }

    class SubmitList implements InputExecutor{

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
                            System.out.println("The god has been deselected. Pls choose another god: ");
                            expectedInput = new GodSelection();
                        }
                        else{           // If it was neither of the two, the player will be notified accordingly
                            System.out.println("\nThe number is not in the list. Pls select another number:\t");
                        }
                    }
                    catch(NumberFormatException exception){
                        System.out.println("\nThis is not a number. Pls Enter a valid input:\t");
                    }
                    catch(IllegalValueException exception){

                    }
                    catch(IllegalActionException exception){

                    }
                }


        }
    }
}
