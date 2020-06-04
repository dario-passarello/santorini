package view.GUI;

import javafx.application.Platform;
import view.ViewManager;
import view.screens.ConnectionErrorScreen;
import view.screens.ConnectionScreen;

public class GUIConnectionError extends ConnectionErrorScreen implements GUIController{


    public GUIConnectionError(ViewManager view) {
        super(view);
    }

    public void closeButton(){
        super.close();
    }


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
