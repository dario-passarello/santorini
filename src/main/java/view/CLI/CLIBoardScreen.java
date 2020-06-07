package view.CLI;

import model.*;
import utils.Coordinate;
import view.IllegalActionException;
import view.IllegalValueException;
import view.ViewManager;
import view.screens.BoardScreen;
import java.util.List;

//TODO Handle all the catches

public class CLIBoardScreen extends BoardScreen implements InputProcessor {

    private InputExecutor expectedInput;
    private boolean additionalPhase = false;

    public CLIBoardScreen(ViewManager view, String activePlayer, List<Player> players, List<Coordinate> preHighlitedCoordinates) {
        super(view, activePlayer, players, preHighlitedCoordinates);
    }

    @Override
    public void processInput(String input) {
        expectedInput.execute(input);
    }



    @Override
    public void onScreenOpen() {


        System.out.println(DrawElements.FLUSH);
        DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers());

        System.out.println("You can place two builders on the board");
        if(activeScreen) System.out.println("Choose where you want to place the first builder (ex. B1)");

        expectedInput = new PlaceBuilder();
    }

    @Override
    public void onScreenClose() {

    }

    //      +-------------------+
    //      +   INNER CLASSES   +
    //      +-------------------+

    /**
     * PLACE BUILDER STATE: This is where the Placement of the builders is handled
     */
    class PlaceBuilder implements InputExecutor{


        @Override
        public void message(){
            if(activeScreen){

                // Find if the player has to place the first builder or the second builder
                String buildnumber;
                int i = 0;
                for(Builder builder : getCurrBuilders()){
                    if(builder.getOwner().equals(getActivePlayer())) i++;
                }
                if(i == 0) buildnumber = "first";
                else buildnumber = "second";

                //Selection message
                System.out.print("Select the square where you want to place the " + buildnumber + " Builder (ex. B1):  ");

                for(Player player : getPlayers()){
                    System.out.println(" - " + player.getName());
                }
            }
            else{
                System.out.print("Waiting for other players to perform their action....  \n");
            }

        }

        @Override
        public void execute(String s) {
            if(!activeScreen){  // Message to be sent if the player should not perform any action

                // Check if the player has placed all the Builders
                int i = 0;
                for(Builder builder : getCurrBuilders()){
                    if(builder.getOwner().equals(getThisPlayerName())) i++;
                }

                if(i == 0) System.out.print("It is not your turn to place the builders. Pls wait for you turn ");
                else System.out.print("You have already placed your builders. Pls wait for the other players' placement ");
                return;
            }
            try{
                Coordinate selectedCoordinate = getCoordinate(s);
                selectSquare(selectedCoordinate);
            }
            catch(NumberFormatException exception){

            } catch (IllegalValueException e) {
                e.printStackTrace();
            } catch (IllegalActionException e) {
                e.printStackTrace();
            }
            catch(IllegalArgumentException e){
                System.out.print("This is an invalid input. Pls try again:\t");
            }

        }
    }

    /**
     * MOVE PHASE STATE: This is where you can either choose your regular move or
     * activate the starting power
     */
    class StartingSelection implements InputExecutor{

        @Override
        public void message() {
            if(activeScreen){
                System.out.print("Select the Builder you want to move (ex. B3) ");
                if(specialPowerAvailable()) System.out.print("\nor type S to activate the special power:  ");
            }
            else System.out.print("MOVE SELECTION PHASE: Pls wait for the other players to make their move");
        }
        @Override
        public void execute(String s) {
            try{
                if(s.toUpperCase().equals("S")){
                    expectedInput = new SpecialPower();
                }
                else{
                        Coordinate selectedCoordinate = getCoordinate(s);
                        //Check that the coordinate is really occupied by a builder
                        //and Highlight the neighborhoods
                        selectSquare(selectedCoordinate);
                        DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers());
                        for(Coordinate coordinate : getHighlightedCoordinates()) {
                            DrawElements.drawSquare(getBoard().squareAt(coordinate), true);
                            System.out.print(DrawElements.ESC + "25H");
                        }
                        // Change to MOVE PHASE
                        expectedInput = new MovePhase();
                        expectedInput.message();

                    }
                }
             catch (IllegalValueException e) {
                e.printStackTrace();
            } catch (IllegalActionException e) {
                System.out.print(e.getMessage() + "Pls insert a valid input  ");
            }
            catch (IllegalArgumentException e){
                System.out.print("This is an invalid input. Pls try again:\t");
            }
        }
    }

    /**
     * MOVE PHASE: This is where you choose where to move the selected builder
     */
    class MovePhase implements InputExecutor{

        @Override
        public void message() {
            if(activeScreen) {
                System.out.print("Choose Where to move the builder (ex. A1) \n" +
                        "or press R to return to the Builder Selection:  ");
            }
        }

        @Override
        public void execute(String s) {
            if(!activeScreen){
                System.out.print("It is not your turn. Pls wait...");
                return;
            }
            if(s.toUpperCase().equals("R")){
                try{
                    if(resetPhaseAvailable()) resetPhase();
                    expectedInput = new StartingSelection();
                    DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers());
                    expectedInput.message();
                }
                catch (IllegalActionException e) {
                    e.getMessage();
                }
            }
            try{
                Coordinate selectedCoordinate = getCoordinate(s);
                expectedInput = new BuildPhase();
                selectSquare(selectedCoordinate);
            }
            catch(IllegalArgumentException e){
            } catch (IllegalValueException e) {
                e.printStackTrace();
            } catch (IllegalActionException e) {
                e.printStackTrace();
            }
        }
    }

    class SpecialPower implements InputExecutor{

        @Override
        public void message() {

        }

        @Override
        public void execute(String s) {

        }
    }

    class AdditionalMove implements InputExecutor{

        @Override
        public void message() {

        }

        @Override
        public void execute(String s) {

        }
    }

    /**
     * BUILD PHASE: This is where you can build your blocks
     */
    class BuildPhase implements InputExecutor{

        @Override
        public void message() {
            if(activeScreen) {
                for(Coordinate coordinate : getHighlightedCoordinates()) {
                    DrawElements.drawSquare(getBoard().squareAt(coordinate), true);
                    System.out.print(DrawElements.ESC + "25H");
                }
                System.out.print("BUILD PHASE: Choose Where you want to build your next block (ex: A1) ");
            }
        }

        @Override
        public void execute(String s) {
            if(!activeScreen){
                System.out.print("It is not your turn. Pls wait...");
                return;
            }
            try{
                Coordinate selectedCoordinate = getCoordinate(s);
                expectedInput = new StartingSelection();
                selectSquare(selectedCoordinate);
            }
            catch(IllegalArgumentException e){
            } catch (IllegalValueException e) {
                e.printStackTrace();
            } catch (IllegalActionException e) {
                e.printStackTrace();
            }
        }
    }

    class AdditionalBuildPhase implements InputExecutor{

        @Override
        public void message() {

        }

        @Override
        public void execute(String s) {

        }
    }



    @Override
    public synchronized void receiveUpdateDone(){
        super.receiveUpdateDone();

        if((getGameState() == Game.State.PLACE_BUILDER)) {
            DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers());
            expectedInput.message();
        }

        if(getTurnState() != null) {
            switch (getTurnState()) {
                case MOVE:
                    DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers());
                    expectedInput = new StartingSelection();
                    expectedInput.message();
                    break;
                case ADDITIONAL_MOVE:
                    DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers());
                    expectedInput = new AdditionalMove();
                    expectedInput.message();
                    break;
                case SPECIAL_MOVE:
                    expectedInput = new SpecialPower();
                    expectedInput.message();
                case BUILD:
                    DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers());
                    expectedInput = new BuildPhase();
                    expectedInput.message();
                    break;
                case ADDITIONAL_BUILD:
                    expectedInput = new AdditionalBuildPhase();
                    expectedInput.message();
                    break;
                default:
                    return;
            }
        }
    }

    // Support Method. To get the coordinate from the input
    private Coordinate getCoordinate(String s){
        if(s.length() != 2) {
            throw new IllegalArgumentException();
        }
        else {
            int column = (int) s.toUpperCase().charAt(0) - 65;
            int line = Integer.parseInt(String.valueOf(s.charAt(1))) - 1;
            Coordinate selectedCoordinate = new Coordinate(line, column);
            return selectedCoordinate;
        }
    }




}
