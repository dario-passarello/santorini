package view.CLI;

import view.IllegalActionException;
import view.IllegalValueException;
import view.ViewManager;
import view.screens.ConnectionScreen;

import java.io.IOException;
import java.util.Scanner;

public class CLIConnectionScreen extends ConnectionScreen {

    public CLIConnectionScreen(ViewManager view){
        super(view);
    }


    @Override
    public void onScreenOpen() {


        //TEMPORARY VISUAL

        Scanner scanner = new Scanner(System.in);


        try {
            print("Select a username:  ");
            String username = scanner.next();
            setUsername(username);

            print("Type an ip Address:  ");
            String ip = scanner.next();
            setIP(ip);

            print("Type a port:   ");
            int port = scanner.nextInt();
            setPort(port);

            print("Select the number of players in the match:  ");
            int number = scanner.nextInt();
            setNumberOfPlayers(number);

            connect();
        }
        catch(IllegalValueException e){

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalActionException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onScreenClose() {

    }

    private void askUsername(Scanner scanner) throws IllegalValueException {
        print("Select a username:  ");
        String username = scanner.next();
        setUsername(username);
    }

    private void print(String s){
        System.out.print(s);
    }
}
