package view.CLI;

import model.Player;
import utils.Coordinate;
import view.IllegalActionException;
import view.IllegalValueException;
import view.ViewManager;
import view.screens.BoardScreen;

import java.util.List;

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
        DrawElements.drawBoard();

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


    class PlaceBuilder implements InputExecutor{

        @Override
        public void execute(String s) {
            if(!activeScreen){
                System.out.println("It is not your turn to place the builders. Pls wait for your turn");
                return;
            }
            int line;
            int column;
            if(s.length() != 2){
                System.out.println("This is an invalid input. Pls try again");
                return;
            }
            try{
                column = (int) s.toUpperCase().charAt(0) - 65;
                line = Integer.parseInt(String.valueOf(s.charAt(1)));
                Coordinate selectedCoordinate = new Coordinate(line, column);
                DrawElements.saveCursor();
                DrawElements.drawBuilder(selectedCoordinate);
                DrawElements.restoreCursor();
                selectSquare(selectedCoordinate);
                System.out.println("Builder Selected");
            }
            catch(NumberFormatException exception){

            } catch (IllegalValueException e) {
                e.printStackTrace();
            } catch (IllegalActionException e) {
                e.printStackTrace();
            }

        }
    }
}
