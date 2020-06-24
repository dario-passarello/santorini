package view.GUI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import view.ViewManager;
import view.screens.GodSelectionScreen;
import view.screens.MenuScreen;

public class GUIMenu extends MenuScreen implements GUIController{

    public GUIMenu(ViewManager view) {
        super(view);
    }

    public void initialize(){
        GUI.getStage().setMaxWidth(1280);
        GUI.getStage().setMinWidth(1280);
        GUI.getStage().setMaxHeight(720);
        GUI.getStage().setMinHeight(720);
    }

    public void startGame(){
        super.goToConnectionScreen();
    }

    public void credits(){
        super.goToCreditsScreen();
    }

    public void quit(){
        super.quitGame();
    }

    @Override
    public String getSceneName() {
        return "menu";
    }

    @Override
    public void onScreenOpen() {
        Platform.runLater(() -> GUI.setSceneController(this));
    }

    @Override
    public void onScreenClose() {

    }
}
