package view.GUI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import view.ViewManager;
import view.screens.GodSelectionScreen;
import view.screens.MenuScreen;

/**
 * This class represents the GUI controller of the menu
 */
public class GUIMenu extends MenuScreen implements GUIController{

    /**
     * GUIMenu constructor
     * @param view the view manager used
     */
    public GUIMenu(ViewManager view) {
        super(view);
    }

    /**
     * initializes the GUIMenu
     */
    public void initialize(){
        GUI.getStage().setMaxWidth(1280);
        GUI.getStage().setMinWidth(1280);
        GUI.getStage().setMaxHeight(720);
        GUI.getStage().setMinHeight(720);
    }

    /**
     * brings the game to the GUIConnection screen
     */
    public void startGame(){
        super.goToConnectionScreen();
    }

    /**
     * brings the game to the GUICredits screen
     */
    public void credits(){
        super.goToCreditsScreen();
    }

    /**
     * ends the game
     */
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
