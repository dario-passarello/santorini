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
        DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers(), getActivePlayer(), getThisPlayerName());

        System.out.println(Colors.YELLOW_227 + "BUILDER PLACEMENT" + Colors.RESET);
        System.out.println("You can place two builders on the board");
        if(activeScreen) System.out.print("Choose where you want to place the first builder (ex. B1) ");


        expectedInput = new PlaceBuilder();
    }

    private String playerColor(){
        int i = 1;
        for(Player player : getPlayers()){
            if(!player.getName().equals(getActivePlayer())) i++;
            else break;
        }
        String color;
        switch(i){
            case 1: color = DrawElements.player1Color; break;
            case 2: color = DrawElements.player2Color; break;
            case 3: color = DrawElements.player3Color; break;
            default: color = Colors.RESET;
        }
        return color;
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
                    if(builder.getOwner().getName().equals(getActivePlayer())) i++;
                }
                if(i == 0) buildnumber = "first";
                else buildnumber = "second";

                //Selection message
                System.out.println(Colors.YELLOW_227 + "BUILDER PLACEMENT" + Colors.RESET);
                System.out.print("Select the square where you want to place the " + buildnumber + " Builder (ex. A3):  ");

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
                    if(builder.getOwner().getName().equals(getThisPlayerName())) i++;
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
                System.out.print(e.getMessage() + "Pls try again:\t");
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
                System.out.println(Colors.YELLOW_227 + "STARTING TURN" + Colors.RESET);
                System.out.print("Select the" + playerColor() + " Builder" + Colors.RESET + " you want to move (ex. A3) ");
                if(specialPowerAvailable()) System.out.print("\nor type S to activate the special power: ");
            }
            else{
                System.out.println(Colors.YELLOW_227 + "STARTING TURN" + Colors.RESET);
                System.out.print("Pls wait for the other players to make their move ");
            }
        }
        @Override
        public void execute(String s) {
            try{
                if(s.toUpperCase().equals("S")){
                    DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers(), getActivePlayer(), getThisPlayerName());
                    toggleSpecialPower();
                    expectedInput = new SpecialPower();
                    expectedInput.message();
                }
                else{
                        Coordinate selectedCoordinate = getCoordinate(s);
                        //Check that the coordinate is really occupied by a builder
                        //and Highlight the neighborhoods
                        selectSquare(selectedCoordinate);
                        DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers(), getActivePlayer(), getThisPlayerName());
                        for(Coordinate coordinate : getHighlightedCoordinates()) {
                            DrawElements.drawSquare(getBoard().squareAt(coordinate), getCurrBuilders(), getPlayers(), true);
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
                System.out.print(e.getMessage() + "Pls insert a valid input ");
            }
            catch (IllegalArgumentException e){
                System.out.print("This is an invalid input. Pls try again: ");
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
                System.out.println(Colors.YELLOW_227 + "MOVE PHASE" + Colors.RESET);
                System.out.print("Select the square where you want to move the builder\n" +
                        "or type R to undo the builder selection ");
            }
            else{
                System.out.println(Colors.YELLOW_227 + "MOVE PHASE" + Colors.RESET);
                System.out.print("Pls wait for the other players to make their move ");
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
                    DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers(), getActivePlayer(), getThisPlayerName());
                    expectedInput.message();
                    return;
                }
                catch (IllegalActionException e) {
                    e.getMessage();
                }
            }
            try{
                Coordinate selectedCoordinate = getCoordinate(s);
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

    /**
     * SPECIAL POWER: It is the state where you where to use Prometheus' power
     */
    class SpecialPower implements InputExecutor{


        @Override
        public void message() {
            if(activeScreen) {
                System.out.println(Colors.YELLOW_227 + "GOD POWER ACTIVATED" + Colors.RESET);
                System.out.print("You have activated the power of your GOD\n" +
                        "Select the " + playerColor() + "builder" + Colors.RESET + " you want to use it on\n" +
                        "(or type R to undo the activation)");
            }
            if(!activeScreen){

            }
        }

        @Override
        public void execute(String s){
            if(!activeScreen){
                System.out.print("It is not your turn. Pls wait...");
                return;
            }
            try{
            if(s.toUpperCase().equals("R")){
                    toggleSpecialPower();
                    expectedInput = new StartingSelection();
                    DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers(), getActivePlayer(), getThisPlayerName());
                    expectedInput.message();
                    return;
            }
                Coordinate selectedCoordinate = getCoordinate(s);
                selectSquare(selectedCoordinate);
                DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers(), getActivePlayer(), getThisPlayerName());

                expectedInput = new BuildPhase(true);
                expectedInput.message();
            }
            catch(IllegalArgumentException e){
            } catch (IllegalValueException e) {
                e.printStackTrace();
            } catch (IllegalActionException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ADDITIONAL STATE: It can either represent an Additional Move or an Additional Build
     */
    class AdditionalState implements InputExecutor{

        private boolean build;
        private String phaseAction;
        private boolean forced;

        public AdditionalState(boolean build){
            this.build = build;

            if(build){
                this.phaseAction = "build";
            }
            else{
                this.phaseAction = "move";
            }
        }

        public AdditionalState(boolean build, boolean forced){
            super();
            this.forced = forced;
        }

        @Override
        public void message() {
            if(activeScreen) {
                for(Coordinate coordinate : getHighlightedCoordinates()) {
                    DrawElements.drawSquare(getBoard().squareAt(coordinate), getCurrBuilders(), getPlayers(), true);
                    System.out.print(DrawElements.ESC + "25H");
                }
                if(forced){
                    System.out.println(Colors.YELLOW_227 + "MOVE PHASE" + Colors.RESET);
                    System.out.print("Select the square where you want to move the builder ");
                    return;
                }
                System.out.println(Colors.YELLOW_227 + "ADDITIONAL " + phaseAction.toUpperCase() + Colors.RESET);
                System.out.print("You can " + phaseAction + " an additional time. Select the Square where you want to " +
                        phaseAction + "\nor type E to end our turn ");
            }
            else{
                if(forced){
                    System.out.println(Colors.YELLOW_227 + "MOVE PHASE" + Colors.RESET);
                    System.out.println("Select the square where you want to move the builder ");
                    return;
                }
                System.out.println(Colors.YELLOW_227 + "ADDITIONAL " + phaseAction.toUpperCase() + Colors.RESET);
                System.out.print("Pls wait for the other players to make their move ");
            }
        }

        @Override
        public void execute(String s) {
            if(!activeScreen){
                System.out.print("It is not your turn. Pls wait... ");
                return;
            }
            if(s.toUpperCase().equals("E")){
                try{
                    if(endPhaseAvailable()) endPhase();
                }
                catch (IllegalActionException e) {
                    e.getMessage();
                }
            }
            try{
                Coordinate selectedCoordinate = getCoordinate(s);
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

    /**
     * BUILD PHASE: This is where you can build your blocks
     */
    class BuildPhase implements InputExecutor{

        private boolean fromSpecial;

        public BuildPhase(boolean fromSpecial){
            this.fromSpecial = fromSpecial;
        }

        public BuildPhase(){ };

        @Override
        public void message() {
            if(activeScreen) {
                for(Coordinate coordinate : getHighlightedCoordinates()) {
                    DrawElements.drawSquare(getBoard().squareAt(coordinate), getCurrBuilders(), getPlayers(), true);
                    System.out.print(DrawElements.ESC + "25H");
                }
                if(fromSpecial){
                    System.out.println(Colors.YELLOW_227 + "GOD POWER ACTIVATED" + Colors.RESET);
                    System.out.println("Select the Square where you want to build on: ");
                    System.out.print("(or type R to undo the builder selection) ");
                    return;
                }
                System.out.println(Colors.YELLOW_227 + "BUILD PHASE" + Colors.RESET);
                System.out.print("Select the Square where you want to build on: ");
                if(specialPowerAvailable()) System.out.print("\nOr press S to use your GOD power ");
            }
            else{
                System.out.println(Colors.YELLOW_227 + "BUILD PHASE" + Colors.RESET);
                System.out.print("Pls wait for the other players to make their move ");
            }
        }

        @Override
        public void execute(String s) {
            if(!activeScreen){
                System.out.print("It is not your turn. Pls wait... ");
                return;
            }
            try{
            if(fromSpecial && s.toUpperCase().equals("R")){
                if(resetPhaseAvailable()) resetPhase();
                expectedInput = new SpecialPower();
                DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers(), getActivePlayer(), getThisPlayerName());
                expectedInput.message();
                return;
            }
            if(s.toUpperCase().equals("S") && specialPowerAvailable()){
                DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers(), getActivePlayer(), getThisPlayerName());
                toggleSpecialPower();
                expectedInput = new SpecialBuild();
                expectedInput.message();
            }
                Coordinate selectedCoordinate = getCoordinate(s);
                selectSquare(selectedCoordinate);
            }
            catch(IllegalArgumentException e){
            } catch (IllegalValueException e) {
                System.out.print(e.getMessage() + "Pls try again ");
            } catch (IllegalActionException e) {
                System.out.print(e.getMessage() + "Pls try again ");
            }
        }
    }

    class SpecialBuild implements InputExecutor{

        @Override
        public void message() {
            for(Coordinate coordinate : getHighlightedCoordinates()) {
                DrawElements.drawSquare(getBoard().squareAt(coordinate), getCurrBuilders(), getPlayers(), true);
                System.out.print(DrawElements.ESC + "25H");
            }
            System.out.println(Colors.YELLOW_227 + "GOD POWER ACTIVATED" + Colors.RESET);
            System.out.println("You can now build a Dome anywhere instead of a block. Choose a square");
            System.out.print("(or type R to undo the god power activation) ");
            return;
        }
        @Override
        public void execute(String s) {
            try{
            if(s.toUpperCase().equals("R")){
                DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers(), getActivePlayer(), getThisPlayerName());
                toggleSpecialPower();
                expectedInput = new BuildPhase();
                expectedInput.message();
                return;
            }
                Coordinate selectedCoordinate = getCoordinate(s);
                selectSquare(selectedCoordinate);
            }
            catch(IllegalArgumentException e){
            } catch (IllegalValueException e) {
                e.printStackTrace();
            } catch (IllegalActionException e) {
                System.out.print(e.getMessage() + "Pls try again ");
            }
        }
    }





    @Override
    public synchronized void receiveUpdateDone(){
        super.receiveUpdateDone();

        if((getGameState() == Game.State.PLACE_BUILDER)) {
            DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers(), getActivePlayer(), getThisPlayerName());
            expectedInput.message();
        }

        if(getTurnState() != null) {
            switch (getTurnState()) {
                case MOVE:
                    DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers(), getActivePlayer(), getThisPlayerName());
                    expectedInput = new StartingSelection();
                    expectedInput.message();
                    break;
                case ADDITIONAL_MOVE:
                    DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers(), getActivePlayer(), getThisPlayerName());
                    expectedInput = new AdditionalState(false);
                    expectedInput.message();
                    break;
                case SPECIAL_MOVE:
                    DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers(), getActivePlayer(), getThisPlayerName());
                    expectedInput = new AdditionalState(false, true);
                    expectedInput.message();
                    break;
                case BUILD:
                    DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers(), getActivePlayer(), getThisPlayerName());
                    expectedInput = new BuildPhase();
                    expectedInput.message();
                    break;
                case ADDITIONAL_BUILD:
                    DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers(), getActivePlayer(), getThisPlayerName());
                    expectedInput = new AdditionalState(true);
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
