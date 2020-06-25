package view.CLI;

import model.*;
import utils.Coordinate;
import view.IllegalActionException;
import view.IllegalValueException;
import view.ViewManager;
import view.screens.BoardScreen;
import java.util.List;



public class CLIBoardScreen extends BoardScreen implements InputProcessor {

    private String inputColor = Colors.GREEN_47;        // The color of the things written in the input
    private InputExecutor currentPhase;

    public CLIBoardScreen(ViewManager view, String activePlayer, List<Player> players, List<Coordinate> preHighlightedCoordinates) {
        super(view, activePlayer, players, preHighlightedCoordinates);
    }

    @Override
    public void processInput(String input) {
        if(input.toUpperCase().equals("INFO")){
            godInfo();
            return;
        }
        currentPhase.execute(input);
    }



    @Override
    public void onScreenOpen() {


        System.out.println(DrawElements.FLUSH);
        refreshBoard();


        currentPhase = new PlaceBuilder();
        currentPhase.message();
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

        refreshBoard();

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
                print(Colors.YELLOW_227 + "BUILDER PLACEMENT" + Colors.RESET + "\n");
                print("You can place two" + playerColor() + " builders" + Colors.RESET + " on the board\n");
                print("Place the " + buildnumber + " Builder (ex. A3): ");

            }
            else{
                print("Waiting for other players to perform their action.... ");
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

                if(i == 0) print("It is not your turn to place the builders. Pls wait for you turn ");
                else print("You have already placed your builders. Pls wait for the other players' placement ");
                return;
            }
            try{
                // Gets the coordinate selected and calls the super screen method
                Coordinate selectedCoordinate = getCoordinate(s);
                selectSquare(selectedCoordinate);
            }
            catch (IllegalValueException e) {
                print(e.getMessage() + ". Pls try again: ");
            } catch (IllegalActionException e) {
                print(e.getMessage() + ". Pls try again: ");
            }
            catch(IllegalArgumentException e){
                print("This is an invalid input. Pls try again: ");
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
                print(Colors.YELLOW_227 + "STARTING TURN" + Colors.RESET + "\n");
                print("Select the" + playerColor() + " Builder" + Colors.RESET + " you want to move (ex. A3) ");
                if(specialPowerAvailable()) print("\n or type S to activate the special power: ");
            }
            else{
                print(Colors.YELLOW_227 + "STARTING TURN" + Colors.RESET + "\n");
                print("Pls wait for the other players to make their move ");
            }
        }
        @Override
        public void execute(String s) {
            try{
                // Refreshes the board and writes the phase message
                if(s.toUpperCase().equals("S")){
                    refreshBoard();
                    toggleSpecialPower();
                    currentPhase = new SpecialPower();
                    currentPhase.message();
                }
                else{
                        Coordinate selectedCoordinate = getCoordinate(s);
                        //Check that the coordinate is really occupied by a builder
                        //and Highlight the neighborhoods
                        selectSquare(selectedCoordinate);
                        refreshBoard();
                        // Change to MOVE PHASE
                        currentPhase = new MovePhase();
                        currentPhase.message();

                    }
                }
             catch (IllegalValueException e) {
                 print(e.getMessage() + ". Pls insert a valid input ");
            } catch (IllegalActionException e) {
                print(e.getMessage() + ". Pls insert a valid input ");
            }
            catch (IllegalArgumentException e){
                print("This is an invalid input. Pls try again: ");
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
                for(Coordinate coordinate : getHighlightedCoordinates()) {
                    DrawElements.drawSquare(getBoard().squareAt(coordinate), getCurrBuilders(), getPlayers(), true);
                    System.out.print(DrawElements.ESC + "25H");
                }
                print(Colors.YELLOW_227 + "MOVE PHASE" + Colors.RESET + "\n");
                print("Select the square where you want to move the builder\n" +
                        " or type R to undo the builder selection ");
            }
            else{
                print(Colors.YELLOW_227 + "MOVE PHASE" + Colors.RESET + "\n");
                print("Pls wait for the other players to make their move ");
            }
        }

        @Override
        public void execute(String s) {
            if(!activeScreen){
                print("It is not your turn. Pls wait... ");
                return;
            }
            if(s.toUpperCase().equals("R")){
                try{
                    // Undo the selection and goes back to the Starting Selection
                    if(resetPhaseAvailable()) resetPhase();
                    refreshBoard();
                    currentPhase = new StartingSelection();
                    currentPhase.message();
                    return;
                }
                catch (IllegalActionException e) {
                    print(e.getMessage() + " ");
                }
            }
            try{
                // Gets the coordinate selected and calls the super screen method
                Coordinate selectedCoordinate = getCoordinate(s);
                selectSquare(selectedCoordinate);
            }
            catch(IllegalArgumentException e){
                print("This is an invalid input. Pls try again: ");
            } catch (IllegalValueException e) {
                print(e.getMessage() + ". Pls insert a valid input ");
            } catch (IllegalActionException e) {
                print(e.getMessage() + ". Pls insert a valid input ");
            }
        }
    }

    /**
     * SPECIAL POWER: It is the state where you can use Prometheus' power
     */
    class SpecialPower implements InputExecutor{


        @Override
        public void message() {
            if(activeScreen) {
                print(Colors.YELLOW_227 + "GOD POWER ACTIVATED" + Colors.RESET + "\n");
                print("You have activated the power of your GOD\n" +
                        " Select the " + playerColor() + "builder" + Colors.RESET + " you want to use it on\n" +
                        " (or type R to undo the activation) ");
            }
        }

        @Override
        public void execute(String s){
            if(!activeScreen){
                print("It is not your turn. Pls wait... ");
                return;
            }
            try{
            if(s.toUpperCase().equals("R")){
                    // Undo the activation and goes back to the Starting Selection
                    toggleSpecialPower();
                    currentPhase = new StartingSelection();
                    refreshBoard();
                    currentPhase.message();
                    return;
            }
                // Gets the coordinate selected and calls the super screen method
                Coordinate selectedCoordinate = getCoordinate(s);
                selectSquare(selectedCoordinate);
                refreshBoard();

                currentPhase = new BuildPhase(true);
                currentPhase.message();
            }
            catch(IllegalArgumentException e){
                print("This is an invalid input. Pls try again: ");
            } catch (IllegalValueException e) {
                print(e.getMessage() + ". Pls insert a valid input ");
            } catch (IllegalActionException e) {
                print(e.getMessage() + ". Pls insert a valid input ");
            }
        }
    }

    /**
     * ADDITIONAL STATE: It can either represent an Additional Move or an Additional Build or a Forced Move
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
                    print(Colors.YELLOW_227 + "MOVE PHASE" + Colors.RESET + "\n");
                    print("Select the square where you want to move the builder ");
                    return;
                }
                print(Colors.YELLOW_227 + "ADDITIONAL " + phaseAction.toUpperCase() + Colors.RESET + "\n");
                print("You can " + phaseAction + " an additional time. Select the Square where you want to " +
                        phaseAction + "\n or type E to end this phase ");
            }
            else{
                if(forced){
                    print(Colors.YELLOW_227 + "MOVE PHASE" + Colors.RESET + "\n");
                    print("Pls wait for the other players to make their move ");
                    return;
                }
                print(Colors.YELLOW_227 + "ADDITIONAL " + phaseAction.toUpperCase() + Colors.RESET + "\n");
                print("Pls wait for the other players to make their move ");
            }
        }

        @Override
        public void execute(String s) {
            if(!activeScreen){
                print("It is not your turn. Pls wait... ");
                return;
            }
            if(s.toUpperCase().equals("E")){
                try{
                    if(endPhaseAvailable()) endPhase();
                }
                catch (IllegalActionException e) {
                    print(e.getMessage() + " ");
                }
            }
            try{
                Coordinate selectedCoordinate = getCoordinate(s);
                selectSquare(selectedCoordinate);
            }
            catch(IllegalArgumentException e){
                print("This is an invalid input. Pls try again: ");
            } catch (IllegalValueException e) {
                print(e.getMessage() + ". Pls insert a valid input ");
            } catch (IllegalActionException e) {
                print(e.getMessage() + ". Pls insert a valid input ");
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
                    print(Colors.YELLOW_227 + "GOD POWER ACTIVATED" + Colors.RESET + "\n");
                    print("Select the Square where you want to build on: ");
                    print("\n (or type R to undo the builder selection) ");
                    return;
                }
                print(Colors.YELLOW_227 + "BUILD PHASE" + Colors.RESET + "\n");
                print("Select the Square where you want to build on: ");
                if(specialPowerAvailable()) print("\n Or press S to use your GOD power ");
            }
            else{
                print(Colors.YELLOW_227 + "BUILD PHASE" + Colors.RESET + "\n");
                print("Pls wait for the other players to make their move ");
            }
        }

        @Override
        public void execute(String s) {
            if(!activeScreen){
                print("It is not your turn. Pls wait... ");
                return;
            }
            try{
            if(fromSpecial && s.toUpperCase().equals("R")){
                // Undo the activatio of Atlas
                if(resetPhaseAvailable()) resetPhase();
                currentPhase = new SpecialPower();
                refreshBoard();
                currentPhase.message();
                return;
            }
            if(s.toUpperCase().equals("S") && specialPowerAvailable()){
                // Use Atlas
                refreshBoard();
                toggleSpecialPower();
                currentPhase = new SpecialBuild();
                currentPhase.message();
            }
                Coordinate selectedCoordinate = getCoordinate(s);
                selectSquare(selectedCoordinate);
            }
            catch(IllegalArgumentException e){
                print("This is an invalid input. Pls try again: ");
            } catch (IllegalValueException e) {
                print(e.getMessage() + ". Pls try again ");
            } catch (IllegalActionException e) {
                print(e.getMessage() + ". Pls try again ");
            }
        }
    }

    /**
     * SPECIAL BUILD PHASE: This is where you can use the power of Atlas
     */
    class SpecialBuild implements InputExecutor{

        @Override
        public void message() {
            for(Coordinate coordinate : getHighlightedCoordinates()) {
                DrawElements.drawSquare(getBoard().squareAt(coordinate), getCurrBuilders(), getPlayers(), true);
                System.out.print(DrawElements.ESC + "25H");
            }
            print(Colors.YELLOW_227 + "GOD POWER ACTIVATED" + Colors.RESET + "\n");
            print("You can now build a Dome anywhere instead of a block. Choose a square\n");
            print("(or type R to undo the god power activation) ");
            return;
        }
        @Override
        public void execute(String s) {
            try{
            if(s.toUpperCase().equals("R")){
                refreshBoard();
                toggleSpecialPower();
                currentPhase = new BuildPhase();
                currentPhase.message();
                return;
            }
                Coordinate selectedCoordinate = getCoordinate(s);
                selectSquare(selectedCoordinate);
            }
            catch(IllegalArgumentException e){
                print("This is an invalid input. Pls try again: ");
            } catch (IllegalValueException e) {
                print(e.getMessage() + ". Pls insert a valid input ");
            } catch (IllegalActionException e) {
                print(e.getMessage() + ". Pls insert a valid input ");
            }
        }
    }





    @Override
    public synchronized void receiveUpdateDone(){
        super.receiveUpdateDone();

        if((getGameState() == Game.State.PLACE_BUILDER)) {
            refreshBoard();
            currentPhase.message();
        }

        if(getTurnState() != null && getGameState()!= Game.State.END_GAME) {
            switch (getTurnState()) {
                case MOVE:
                    refreshBoard();
                    currentPhase = new StartingSelection();
                    currentPhase.message();
                    break;
                case ADDITIONAL_MOVE:
                    refreshBoard();
                    currentPhase = new AdditionalState(false);
                    currentPhase.message();
                    break;
                case SPECIAL_MOVE:
                    refreshBoard();
                    currentPhase = new AdditionalState(false, true);
                    currentPhase.message();
                    break;
                case BUILD:
                    refreshBoard();
                    currentPhase = new BuildPhase();
                    currentPhase.message();
                    break;
                case ADDITIONAL_BUILD:
                    refreshBoard();
                    currentPhase = new AdditionalState(true);
                    currentPhase.message();
                    break;
                default:
                    return;
            }
        }
    }

    // Support Method. To get the coordinate from the input
    private Coordinate getCoordinate(String s) throws IllegalArgumentException{
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

    // Support Method. Draws the Board
    private void refreshBoard(){
        DrawElements.refreshBoard(getBoard(), getCurrBuilders(), getPlayers(), getActivePlayer(), getThisPlayerName());
    }

    // Support Method Draws the Information about the gods
    private void godInfo(){
        refreshBoard();
        currentPhase.message();
        DrawElements.saveCursor();
        DrawElements.writeGodInfo(getPlayers());
        DrawElements.restoreCursor();
        System.out.print(" "); // Literally the space before the boundary
    }

    private void print(String s){
        System.out.print(Colors.RESET + " " + s + Colors.GREEN_47);
    }




}
