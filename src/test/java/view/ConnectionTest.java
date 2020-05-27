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

public class ConnectionTest {
    //@Test
    public void test(){

        ViewManager manager = new ViewManager();
        Screen firstScreen = new GUIConnection(manager);
        GUI.setLaunchController((GUIController) firstScreen);
        Application.launch(GUI.class);
        manager.changeActiveScreen(firstScreen);


    }
}
