package view.CLI;

import view.IllegalActionException;
import view.IllegalValueException;
import view.ViewManager;
import view.screens.ConnectionScreen;

import java.io.IOException;
import java.util.Scanner;

public class CLIConnectionScreen extends ConnectionScreen implements InputProcessor {

    private InputExecutor expectedInput;


    public CLIConnectionScreen(ViewManager view){
        super(view);
    }


    @Override
    public void onScreenOpen() {


        //TEMPORARY VISUAL

 //      expectedInput = new Username();
 //      System.out.print("Enter a username:          ");

        setting();




    }

    @Override
    public void onScreenClose() {

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
            setPort(12345);
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
        public void execute(String s) {
            try {
                setUsername(s);
                expectedInput = new Ip();
                System.out.print("Enter an IP Address:        ");
            }
            catch(IllegalValueException exception){
                System.out.print(exception.getMessage() + ": Please Enter a new one:   ");
            }
        }
    }

    class Ip implements  InputExecutor{
        @Override
        public void execute(String s) {
            try {
                setIP(s);
                expectedInput = new Port();
                System.out.print("Enter a port number:        ");
            }
            catch(IllegalValueException exception){
                System.out.print(exception.getMessage() + ": Please Enter an IP Address:  ");
            }
        }
    }

    class Port implements InputExecutor{
        @Override
        public void execute(String s) {
            try {
                setPort(Integer.parseInt(s));
                expectedInput = new NumberofPlayers();
                System.out.print( "Enter the type of Lobby you want to Join: \n" +
                                    "(2) - 2 Player Matches\n" +
                                    "(3) - 3 Player Matches\n" +
                                    "            ");
            }
            catch(IllegalValueException exception){
                System.out.print(exception.getMessage() + ": Please Enter an IP Address:  ");
            }

        }
    }

    class NumberofPlayers implements InputExecutor{
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
        }
    }
}
