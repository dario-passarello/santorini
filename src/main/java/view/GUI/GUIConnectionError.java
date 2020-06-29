package view.GUI;

import javafx.application.Platform;
import view.ViewManager;
import view.screens.ConnectionErrorScreen;
import view.screens.ConnectionScreen;

/**
 * This class represents the GUI controller during a connection error
 */
public class GUIConnectionError extends ConnectionErrorScreen implements GUIController{


    /**
     * GUIConnectionError constructor
     * @param view the view manager used
     */
    public GUIConnectionError(ViewManager view) {
        super(view);
    }

    /**
     * brings the game to the GUIMenu screen
     */
    public void closeButton(){
        super.close();
    }


    public void onScreenOpen() {
        super.onScreenOpen();
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
