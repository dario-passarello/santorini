package view.GUI;

import javafx.application.Platform;
import view.ViewManager;
import view.screens.CreditsScreen;

public class GUICredits extends CreditsScreen implements GUIController{

    public GUICredits(ViewManager view) {
        super(view);
    }

    public void initialize(){
        GUI.getStage().setMaxWidth(1280);
        GUI.getStage().setMinWidth(1280);
        GUI.getStage().setMaxHeight(720);
        GUI.getStage().setMinHeight(720);
    }

    public void menu(){
        super.goToMenuScreen();
    }

    @Override
    public void onScreenOpen() {
        Platform.runLater(() -> GUI.setSceneController(this));
    }

    @Override
    public String getSceneName() {
        return "credits";
    }
}
