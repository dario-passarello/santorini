package view.GUI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.Player;
import view.AssetLoader;
import view.IllegalValueException;
import view.ViewManager;
import view.screens.PickGodScreen;

import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class GUIPickGod extends PickGodScreen implements GUIController {

    @FXML HBox godGraphics;
    @FXML HBox token;
    @FXML HBox keyboard;
    @FXML Label playerTurn;

    private ImageView godImage;
    private ImageView tokenImage;
    private Button button;
    private Image img;

    public GUIPickGod(ViewManager view, String firstActivePlayer, List<String> availableGods) {
        super(view, firstActivePlayer, availableGods);
    }

    public void initialize(){
        GUI.getStage().setMaxWidth(1280);
        GUI.getStage().setMinWidth(1280);
        GUI.getStage().setMaxHeight(720);
        GUI.getStage().setMinHeight(720);

        updateNameLabel();

        for(String god : getAllGodToChoose()){
            //Creating god images
            godImage = new ImageView();
            godImage.setPreserveRatio(true);
            godImage.setFitHeight(350);
            Image img = AssetLoader.getGodAssetsBundle(god).loadGodCardImage();
            godImage.setImage(img);
            //godImage.setStyle("-fx-border-color: black; -fx-border-width: 10;");
            godGraphics.getChildren().add(godImage);

            //Creating keyboard
            button = new Button();
            button.setPrefSize(210,350);
            button.setOpacity(0);
            Tooltip description = new Tooltip();
            description.setText(god.toUpperCase() + ":\n" + AssetLoader.getGodAssetsBundle(god).getDescription());
            button.setTooltip(description);
            keyboard.getChildren().add(button);

            //Creating token
            tokenImage = new ImageView();
            tokenImage.setPreserveRatio(true);
            tokenImage.setFitHeight(350);
            URL url = getClass().getResource("/assets/taken.png");
            tokenImage.setImage(new Image(String.valueOf(url)));
            tokenImage.setOpacity(0.5);
            tokenImage.setVisible(false);
            token.getChildren().add(tokenImage);

            //Players distinction
            if(isActiveScreen()){
                button.setOnMouseClicked((event) -> {
                    try {
                        pickGod(god);
                    } catch (IllegalValueException ignored) {
                        //ignored.printStackTrace();
                    }
                });
            }
        }


    }


    private void updateNameLabel(){
        if(getThisPlayerName().equals(getActivePlayerName())){
            playerTurn.setText("It's your turn!");
        } else {
            playerTurn.setText("It's " + getActivePlayerName() + "'s turn!");
        }
    }

    @Override
    public void onScreenOpen() {
        Platform.runLater(() -> GUI.setSceneController(this));
    }

    @Override
    public void onScreenClose() {

    }

    @Override
    public void receiveUpdateDone() {
        super.receiveUpdateDone();

        Platform.runLater(this::updateNameLabel);

        for(int i = 0; i < getNumberOfPlayers(); i++){
            if(!getGodsRemaining().contains(getAllGodToChoose().get(i))){               //disable unavailable gods
                token.getChildren().get(i).setVisible(true);
            }
        }
    }


    @Override
    public void receivePlayerList(List<Player> list){
        super.receivePlayerList(list);
        super.getGodsRemaining();
    }

    @Override
    public String getSceneName() {
        return "pickGod";
    }
}
