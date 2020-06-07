package network;

import view.CLI.CLIScreenFactory;
import view.GUI.GUIScreenFactory;
import view.ViewManager;
import view.screens.ScreenFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    public static final Logger logger = Logger.getLogger(Client.class.getName());


    public static void main(String[] args) {
        logger.setLevel(Level.INFO);
        ScreenFactory factory;
        ViewManager viewManager = new ViewManager();
        if(args.length == 1 && args[0].equals("-cli")) {
            factory = new CLIScreenFactory(viewManager);
        } else {
            factory = new GUIScreenFactory(viewManager);
        }
        viewManager.setScreenFactory(factory);
        viewManager.run();
    }
}
