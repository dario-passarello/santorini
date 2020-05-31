package view.screens;

import javafx.application.Platform;
import view.GUI.GUI;
import view.GUI.GUIController;
import view.ViewManager;

public abstract class ConnectionErrorScreen extends Screen implements  GUIController {

    public ConnectionErrorScreen(ViewManager view) {
        super(view);
    }

    protected final void close(){
        view.changeActiveScreen(view.getScreenFactory().getMenuScreen());
    }

    @Override
    public void onScreenOpen() {
        Platform.runLater(() -> GUI.setSceneController(this));
    }

    @Override
    public void onScreenClose() {

    }

    @Override
    public String getSceneName() {
        return "connectionError";
    }
}
