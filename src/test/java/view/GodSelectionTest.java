package view;

import javafx.application.Application;
import network.Client;
import network.Server;
import org.junit.jupiter.api.Test;
import view.GUI.GUI;
import view.GUI.GUIConnection;
import view.GUI.GUIController;
import view.GUI.GUIGodSelection;
import view.screens.Screen;

import java.util.ArrayList;
import java.util.List;

public class GodSelectionTest {
    //@Test
    public void test(){
        //Server server = new Server();
        //new Thread(server).start();

        ViewManager manager = new ViewManager();
        manager.setPlayersNames(List.of("Baluardo","Marcantonio","Edoardo"));
        manager.setNumberOfPlayers(3);
        manager.setThisPlayerName("Baluardo");
        Screen firstScreen = new GUIGodSelection(manager,"Marcantonio");
        GUI.setLaunchController((GUIController) firstScreen);
        Application.launch(GUI.class);
        manager.changeActiveScreen(firstScreen);


    }
}
