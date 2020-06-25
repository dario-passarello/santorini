package network;

import view.CLI.CLIScreenFactory;
import view.GUI.GUIScreenFactory;
import view.ViewManager;
import view.screens.ScreenFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Static class for the Client-side application. Launches the CLI or the GUI
 */
public class Client {

    /**
     * Client Side Logger. Dumps the log on stderr
     */
    public static final Logger logger = Logger.getLogger(Client.class.getName());

    /**
     * Launch the Client
     * @param GUI If true a new GUI will launch, if false a CLI will launch
     */
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
