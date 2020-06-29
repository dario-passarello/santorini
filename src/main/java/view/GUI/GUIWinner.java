package view.GUI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Player;
import view.AssetLoader;
import view.ViewManager;
import view.screens.WinnerScreen;

import java.util.List;

/**
 * This class represents the GUI controller at the end of a game
 */
public class GUIWinner extends WinnerScreen implements GUIController {

    @FXML Label winnerLabel;
    @FXML ImageView winnerGod;
    @FXML Button menuButton;

    List<Player> players;

    /**
     * GUIWinner constructor
     * @param view the view manager used
     * @param players the list of player in the game
     */
    public GUIWinner(ViewManager view, List<Player> players) {
        super(view);
        this.players = players;
    }


    /**
     * initializes the GUIWinner
     */
    public void initialize(){
        GUI.getStage().setMaxWidth(1280);
        GUI.getStage().setMinWidth(1280);
        GUI.getStage().setMaxHeight(720);
        GUI.getStage().setMinHeight(720);

        Player winner = getWinner(players);
        if(winner.getName().equals(getThisPlayerName())){
            winnerLabel.setText("YOU WON!");
        } else {
            winnerLabel.setText(winner.getName().toUpperCase() + " WON!");
        }
        Image img = AssetLoader.getGodAssetsBundle(winner.getGod().getName()).loadGodFigureImage();
        winnerGod.setImage(img);
        winnerGod.setPreserveRatio(true);
        winnerGod.setFitHeight(400);
    }

    /**
     * brings the game to the GUIMenu screen
     */
    public void goToMenuScreenListener(){
        super.goToMenuScreen();
    }

    @Override
    public void onScreenOpen() {
        super.onScreenOpen();
        Platform.runLater(() -> GUI.setSceneController(this));
    }

    @Override
    public void onScreenClose() {

    }


    @Override
    public String getSceneName() {
        return "winner";
    }
}
