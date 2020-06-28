package view.CLI;

import view.IllegalActionException;
import view.IllegalValueException;
import view.ViewManager;
import view.screens.ConnectionErrorScreen;
import view.screens.ConnectionScreen;

import java.io.IOException;
import java.util.Scanner;


/**
 * This class represents the screen that is shown when the player chooses to start the game
 */
public class CLIConnectionScreen extends ConnectionScreen implements InputProcessor {

    private InputExecutor expectedInput;
    private boolean connectionFailed = false;


    /**
     * Standard constructor
     * @param view The client caller
     */
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
        if(expectedInput instanceof Connecting){
            if (((Connecting) expectedInput).connection != null)((Connecting) expectedInput).connection.interrupt();
        }
    }




    @Override
    public void processInput(String input) {

        // If you make a mistake you can restart by typing "Redo"
        if(input.toUpperCase().equals("RESTART") && !(expectedInput instanceof Connecting)){
            System.out.println(DrawElements.FLUSH);
            onScreenOpen();
        }
        else expectedInput.execute(input);
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


    /**
     * This class represents the moment where the user is selecting the username
     */
    class Username implements InputExecutor{

        @Override
        public void message(){
            print("Enter a username: ");
        }
        @Override
        public void execute(String s) {
            try {
                setUsername(s);
                expectedInput = new Ip();
                expectedInput.message();
            }
            catch(IllegalValueException exception){
                print(exception.getMessage() + ": Please Enter a new one:   ");
            }
        }
    }

    /**
     * This class represents the moment where the user is selecting the ip Address of the server
     */
    class Ip implements  InputExecutor{
        @Override
        public void message(){
            print("Enter an IP Address: ");
        }

        @Override
        public void execute(String s) {
            try {
                setIP(s);
                expectedInput = new Port();
                expectedInput.message();
            }
            catch(IllegalValueException exception){
                print(exception.getMessage() + ": Please Enter an IP Address:  ");
            }
        }
    }

    /**
     * This class represents the moment where player is typing the tcp port to connect to the server
     */
    class Port implements InputExecutor{
        @Override
        public void message(){
            print("Enter a port number: ");
        }
        @Override
        public void execute(String s) {
            try {
                setPort(s);
                expectedInput = new NumberofPlayers();
                expectedInput.message();

            }
            catch(IllegalValueException exception){
                print(exception.getMessage() + ": Please enter a valid input:  ");
            }
            catch(NumberFormatException exception){
                print("The input is not as not a number. Pls enter a valid input:  ");
            }

        }
    }

    /**
     * This class represents the moment where the player selects the type of lobby he wants to join
     */
    class NumberofPlayers implements InputExecutor{

        public transient Thread connection = null;
        @Override
        public void message(){
            print("Enter the type of Lobby you want to Join: \n" +
                    "      (2) - 2 Player Matches\n" +
                    "      (3) - 3 Player Matches");

            DrawElements.moveRight(15);
            DrawElements.moveUp(2);
            DrawElements.out.flush();
        }
        @Override
        public void execute(String s) {
            try {
                setNumberOfPlayers(Integer.parseInt(s));
                    DrawElements.moveDown(4);
                    DrawElements.out.flush();
                    expectedInput = new Connecting(connection);
                    expectedInput.message();

                connection =  new Thread(() ->{
                    try {
                        connect();
                    } catch (IllegalActionException exception) {
                        print(exception.getMessage() + " ");
                    } catch (IOException exception) {
                        print("\n Connection Error. Type REDO to start again:  ");
                        connectionFailed = true;
                    }
                });
                connection.start();
            }
            catch(NumberFormatException exception){
                print("The input is not a number. Pls enter a valid input:  ");
            }
        }
    }

    /**
     * This class represents the moment where the game is looking for a game
     */
    class Connecting implements InputExecutor{

        private Thread connection;
        public Connecting(Thread thread){
            this.connection = thread;
        }

        @Override
        public void message() {
            print(DrawElements.inputColor + "TRYING TO FIND A GAME.   PLS WAIT.... ");
        }

        @Override
        public void execute(String s) {
            if(s.toUpperCase().equals("REDO") && connectionFailed) {        // If you type REDO restart the connection
                System.out.println(DrawElements.FLUSH);
                connectionFailed = false;
                onScreenClose();
                onScreenOpen();
            }
            else
                if(connectionFailed) print(" NOT A VALID INPUT. Pls try Again ");
                else print(" TRYING TO FIND A GAME.   PLS WAIT.... ");
        }
    }



    private void print(String s){
        System.out.print(Colors.RESET + " " + s + DrawElements.inputColor);
    }

}
