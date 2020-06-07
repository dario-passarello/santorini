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

public class GUIWinner extends WinnerScreen implements GUIController {

    @FXML Label winnerLabel;
    @FXML ImageView winnerGod;
    @FXML Button menuButton;

    List<Player> players;

    public GUIWinner(ViewManager view, List<Player> players) {
        super(view);
        this.players = players;
    }


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

    public void goToMenuScreenListener(){
        super.goToMenuScreen();
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
        return "winner";
    }
}
