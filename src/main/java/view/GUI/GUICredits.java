package view.GUI;

import javafx.application.Platform;
import view.ViewManager;
import view.screens.CreditsScreen;

/**
 * This class represents the GUI controller in the Credits screen
 */
public class GUICredits extends CreditsScreen implements GUIController{

    /**
     * GUICredits constructor
     * @param view the view manager used
     */
    public GUICredits(ViewManager view) {
        super(view);
    }

    /**
     * initializes the GUICredits
     */
    public void initialize(){
        GUI.getStage().setMaxWidth(1280);
        GUI.getStage().setMinWidth(1280);
        GUI.getStage().setMaxHeight(720);
        GUI.getStage().setMinHeight(720);
    }

    /**
     * brings the game to the GUIMenu screen
     */
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
