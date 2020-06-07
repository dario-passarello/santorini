package view.CLI;

import view.IllegalActionException;
import view.IllegalValueException;
import view.ViewManager;
import view.screens.ConnectionScreen;

import java.io.IOException;
import java.util.Scanner;


//TODO Block input responses while waiting for a match to be found (easy)


public class CLIConnectionScreen extends ConnectionScreen implements InputProcessor {

    private InputExecutor expectedInput;

    public CLIConnectionScreen(ViewManager view){
        super(view);
    }


    @Override
    public void onScreenOpen() {

        DrawElements.drawTitle(Colors.WHITE_231, Colors.BLUE_27);
        System.out.println("\n\n");

         expectedInput = new Username();
         expectedInput.message();

    }

    @Override
    public void onScreenClose() {
        System.out.print(Colors.RESET);
    }


    private void print(String s){
        System.out.print(s);
    }

    @Override
    public void processInput(String input) {
        expectedInput.execute(input);
    }

    //TESTING ONLY
    private void setting(){
        try {
            setUsername("Mario");
            setIP("127.0.0.1");
            setPort("12345");
            setNumberOfPlayers(2);
            connect();
        }
        catch(IllegalValueException e){

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalActionException e) {
            e.printStackTrace();
        }

    }

    //      +-------------------+
    //      +   INNER CLASSES   +
    //      +-------------------+


    class Username implements InputExecutor{

        @Override
        public void message(){
            System.out.print(Colors.RESET + "Enter a username: " + Colors.GREEN_47);
        }
        @Override
        public void execute(String s) {
            try {
                setUsername(s);
                expectedInput = new Ip();
                expectedInput.message();
            }
            catch(IllegalValueException exception){
                System.out.print(exception.getMessage() + ": Please Enter a new one:   ");
            }
        }
    }

    class Ip implements  InputExecutor{
        @Override
        public void message(){
            System.out.print(Colors.RESET + "Enter an IP Address: " + Colors.GREEN_47);
        }

        @Override
        public void execute(String s) {
            try {
                setIP(s);
                expectedInput = new Port();
                expectedInput.message();
            }
            catch(IllegalValueException exception){
                System.out.print(Colors.RESET + exception.getMessage() + ": Please Enter an IP Address:  " + Colors.GREEN_47);
            }
        }
    }

    class Port implements InputExecutor{
        @Override
        public void message(){
            System.out.print(Colors.RESET + "Enter a port number: " + Colors.GREEN_47);
        }
        @Override
        public void execute(String s) {
            try {
                setPort(s);
                expectedInput = new NumberofPlayers();
                expectedInput.message();

            }
            catch(IllegalValueException exception){
                System.out.print(Colors.RESET + exception.getMessage() + ": Please enter a valid input:  " + Colors.GREEN_47);
            }
            catch(NumberFormatException exception){

            }

        }
    }

    class NumberofPlayers implements InputExecutor{
        @Override
        public void message(){
            System.out.print(Colors.RESET + "Enter the type of Lobby you want to Join: \n" +
                    "      (2) - 2 Player Matches\n" +
                    "      (3) - 3 Player Matches" + Colors.GREEN_47);
            DrawElements.moveUp(2);
            DrawElements.moveRight(14);
            DrawElements.out.flush();
        }
        @Override
        public void execute(String s) {
            try {
                setNumberOfPlayers(Integer.parseInt(s));
                    connect();
            }
            catch(IllegalActionException actionexception){

            }
            catch(IOException ioexception){

            }
            catch(NumberFormatException exception){

            }
        }
    }
}
