package view.screens;

import javafx.application.Platform;
import view.GUI.GUI;
import view.GUI.GUIController;
import view.ViewManager;

/**
 * In this screen is displayed a connection error message
 * <p>From this screen it's possible to:</p>
 * <p>- Return to {@link MenuScreen}</p>
 */
public abstract class ConnectionErrorScreen extends Screen implements  GUIController {

    public ConnectionErrorScreen(ViewManager view) {
        super(view);
    }

    /**
     * Close this screen and return to {@link MenuScreen}
     */
    protected final void close(){
        view.changeActiveScreen(view.getScreenFactory().getMenuScreen());
    }

    @Override
    public void onScreenOpen() {
        try{
            view.closeConnection();
        } catch (IllegalStateException e){
            //Do nothing, the connection was already closed
        }

    }

    @Override
    public void onScreenClose() {

    }

    @Override
    public String getSceneName() {
        return "connectionError";
    }
}
