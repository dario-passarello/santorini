package network;

import view.CLI.CLIScreenFactory;
import view.GUI.GUIScreenFactory;
import view.ViewManager;
import view.screens.ScreenFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    public static final Logger logger = Logger.getLogger(Client.class.getName());


    public static void startClient(boolean GUI) {
        logger.setLevel(Level.INFO);
        ScreenFactory factory;
        ViewManager viewManager = new ViewManager();
        if(!GUI) {
            factory = new CLIScreenFactory(viewManager);
        } else {
            factory = new GUIScreenFactory(viewManager);
        }
        viewManager.setScreenFactory(factory);
        viewManager.run();
    }
}
