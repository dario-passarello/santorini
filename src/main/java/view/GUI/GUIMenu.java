package view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import view.ViewManager;
import view.screens.GodSelectionScreen;
import view.screens.MenuScreen;

public class GUIMenu extends MenuScreen implements GUIController{

    public GUIMenu(ViewManager view) {
        super(view);
    }

    public void startGame(){
        //TODO
    }

    public void credits(){
        //TODO
    }

    public void quit(){
        //TODO
    }

    @Override
    public String getSceneName() {
        return "menu";
    }

    @Override
    public void onScreenOpen() {

    }

    @Override
    public void onScreenClose() {

    }
}
