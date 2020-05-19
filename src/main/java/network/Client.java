package network;

import view.CLI.CLIScreenFactory;
import view.ScreenFactory;

public class Client {
    public static void main(String[] args) {
        ScreenFactory factory;
        if(args.length < 2){
            factory = new CLIScreenFactory();
        }


    }
}
