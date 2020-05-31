package view.GUI;

import javafx.application.Platform;
import view.ViewManager;
import view.screens.ConnectionScreen;

public class GUIConnectionError extends ConnectionScreen implements GUIController{


    public GUIConnectionError(ViewManager view) {
        super(view);
    }

    @Override
    public void onScreenOpen() {
        Platform.runLater(() ->GUI.setSceneController(this));
    }

    @Override
    public void onScreenClose() {

    }

    @Override
    public String getSceneName() {
        return "connectionError";
    }
}
