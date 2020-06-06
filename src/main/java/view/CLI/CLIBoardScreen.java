package view.CLI;

import model.Board;
import model.Builder;
import model.Game;
import model.Player;
import utils.Coordinate;
import view.IllegalActionException;
import view.IllegalValueException;
import view.ViewManager;
import view.screens.BoardScreen;
import java.util.List;

//TODO Handle all the catches

public class CLIBoardScreen extends BoardScreen implements InputProcessor {

    InputExecutor expectedInput;

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
        DrawElements.drawBoard(DrawElements.GREENBG, DrawElements.WHITE);

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
            int line;
            int column;
            if(s.length() != 2){
                System.out.print("This is an invalid input. Pls try again:\t");
                return;
            }
            try{
                column = (int) s.toUpperCase().charAt(0) - 65;
                line = Integer.parseInt(String.valueOf(s.charAt(1))) - 1;
                Coordinate selectedCoordinate = new Coordinate(line, column);
                selectSquare(selectedCoordinate);
            }
            catch(NumberFormatException exception){

            } catch (IllegalValueException e) {
                e.printStackTrace();
            } catch (IllegalActionException e) {
                e.printStackTrace();
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
                if(specialPowerAvailable()) System.out.print("\n or type S to activate the special power:  ");
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
                    if(s.length() != 2) {
                        System.out.print("This is an invalid input. Pls try again:\t");
                        return;
                    }
                    else{
                        // Transform builder Selection into coordinate
                        int column = (int) s.toUpperCase().charAt(0) - 65;
                        int line = Integer.parseInt(String.valueOf(s.charAt(1))) - 1;
                        Coordinate selectedCoordinate = new Coordinate(line, column);

                        //Check that the coordinate is really occupied by a builder
                        //and Highlight the neighborhoods
                        selectSquare(selectedCoordinate);
                        DrawElements.refreshBoard(getBoard(), getCurrBuilders());
                        for(Coordinate coordinate : getHighlightedCoordinates()) {
                            DrawElements.saveCursor();
                            DrawElements.drawSquare(getBoard().squareAt(coordinate), true);
                            DrawElements.restoreCursor();
                        }
                        expectedInput = new MovePhase();
                        expectedInput.message();

                    }
                }
            } catch (IllegalValueException e) {
                e.printStackTrace();
            } catch (IllegalActionException e) {
                System.out.print(e.getMessage() + "Pls insert a valid input  ");
            }
        }
    }

    class MovePhase implements InputExecutor{

        @Override
        public void message() {
            System.out.print("Choose Where to move the builder: ");
        }

        @Override
        public void execute(String s) {

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

    @Override
    public synchronized void receiveBoard(Board board) {
        super.receiveBoard(board);


        DrawElements.refreshBoard(board, getCurrBuilders());
        System.out.print(DrawElements.ESC + "24H");
        if(getGameState() == Game.State.TURN && expectedInput instanceof PlaceBuilder){
            expectedInput = new StartingSelection();
        }
        expectedInput.message();
    }

    @Override
    public synchronized void receiveGameState(Game.State state, Player activePlayer) {
        super.receiveGameState(state, activePlayer);


    }




}
