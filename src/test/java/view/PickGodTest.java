package view;

import javafx.application.Application;
import org.junit.jupiter.api.Test;
import view.GUI.GUI;
import view.GUI.GUIController;
import view.GUI.GUIPickGod;
import view.screens.Screen;

import java.util.List;

public class PickGodTest {
    @Test
    public void test(){

        ViewManager manager = new ViewManager();
        manager.setPlayersNames(List.of("Baluardo","Marcantonio","Edoardo"));
        manager.setNumberOfPlayers(3);
        manager.setThisPlayerName("Baluardo");
        Screen firstScreen = new GUIPickGod(manager, "Baluardo",List.of("Apollo","Minotaur"));
        GUI.setLaunchController((GUIController) firstScreen);
        Application.launch(GUI.class);
        manager.changeActiveScreen(firstScreen);

    }
}
