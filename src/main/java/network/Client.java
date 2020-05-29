package network;

import view.CLI.CLIScreenFactory;
import view.GUI.GUIScreenFactory;
import view.ViewManager;
import view.screens.ScreenFactory;

import java.util.logging.Logger;

public class Client {

    public static final Logger logger = Logger.getLogger(Client.class.getName());

    public static void main(String[] args) {
        ScreenFactory factory;
        ViewManager viewManager = new ViewManager();
 //       if(args.length == 2 && args[1].equals("-cli")) {
            factory = new CLIScreenFactory(viewManager);
   //     } else {
   //         factory = new GUIScreenFactory(viewManager);
   //     }
        viewManager.setScreenFactory(factory);
        viewManager.run();
    }
}
