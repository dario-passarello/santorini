package view.GUI;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.AssetLoader;
import view.IllegalActionException;
import view.IllegalValueException;
import view.ViewManager;
import view.screens.GodSelectionScreen;

import java.net.URL;

public class GUIGodSelection extends GodSelectionScreen implements GUIController {
    @FXML StackPane rootPane;
    @FXML GridPane keyboard;
    @FXML GridPane godGraphics;
    @FXML GridPane selected;
    @FXML ImageView bigCover;
    @FXML ImageView buttonGraphic;
    @FXML Text description;
    @FXML Button submit;
    @FXML Label wait;


    public GUIGodSelection(ViewManager view, String activePlayer)  {
        super(view, activePlayer);
    }


    public void initialize(){
        GUI.getStage().setMaxWidth(1280);
        GUI.getStage().setMinWidth(1280);
        GUI.getStage().setMaxHeight(720);
        GUI.getStage().setMinHeight(720);

        int numGod = 0;
        bigCover.setPreserveRatio(true);
        bigCover.setFitHeight(470);
        description.setStyle("-fx-text-fill: darkblue;-fx-font-family: 'Arial Black';-fx-font-size: 15pt");
        displayDefault();

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 5; j++) {
                //Creating buttons
                Button button = new Button(Integer.toString(numGod));
                button.setMaxSize(400,400);
                button.setOpacity(0);
                keyboard.add(button, j, i, 1, 1);

                //Creating god images
                ImageView godImage = new ImageView();
                //URL url = getClass().getResource("/gods/"+numGod+".png");
                Image img = AssetLoader.getGodAssetsBundle(numGod).loadGodCardImage();
                godImage.setImage(img);
                godImage.setPreserveRatio(true);
                godImage.setFitHeight(200);
                godGraphics.add(godImage, j, i, 1, 1);

                //Creating ImageViews for token
                ImageView token = new ImageView();
                selected.add(token, j, i, 1, 1);


                //Setting listeners
                int finalNumGod = numGod;
                Image bigGodImage = AssetLoader.getGodAssetsBundle(finalNumGod).loadGodCardImage();
                button.setOnMouseEntered((event) -> showGod(bigGodImage, finalNumGod));
                button.setOnMouseExited((event -> displayDefault()));

                //Players distinction
                if(isActiveScreen()){
                    button.setOnMouseClicked((event) -> takeClickedGod(finalNumGod));
                    submit.setOnMouseClicked((event) -> {
                        try {
                            submitGodList();
                        } catch (IllegalActionException ignored) {}
                    });
                } else {
                    buttonGraphic.setOpacity(0);
                    wait.setStyle("-fx-text-fill: darkred;-fx-font: bold; -fx-font-family: 'Arial Black' ;-fx-font-size: 15");
                    wait.setText("Another player is choosing the gods!");
                }
                numGod++;
            }
        }
    }

    public void showGod(Image img, int numGod) {
        displayHoveredGod(img);
        showDescription(numGod);
    }

    public void displayDefault(){
        URL url = getClass().getResource("/assets/placeholder.png");
        bigCover.setImage(new Image(url.toExternalForm()));
        description.setText("Select a god");
    }

    public void displayHoveredGod(Image img){
        bigCover.setImage(img);
    }

    public void showDescription(int godID) {
        this.description.setText(AssetLoader.getGodAssetsBundle(godID).getDescription());
    }

    
    public void takeClickedGod(int numGod){
        ImageView selectedGod = (ImageView) GUI.getNodeFromGridPane(selected, numGod%5, numGod/5);
        assert selectedGod != null;
        if(!isGodChosen(AssetLoader.getGodNameFromID(numGod))) {
            try{
                addGod(AssetLoader.getGodNameFromID(numGod));
                URL url = getClass().getResource("/assets/token.png");
                selectedGod.setImage(new Image(String.valueOf(url)));
                selectedGod.setPreserveRatio(true);
                selectedGod.setFitHeight(200);
                selectedGod.setOpacity(0.5);
                if(readyToSubmit()) enableSubmit();
            } catch (IllegalValueException | IllegalActionException ignored) {}
        } else {
            try {
                removeGod(AssetLoader.getGodNameFromID(numGod));
                selectedGod.setImage(null);
                disableSubmit();
            } catch (IllegalValueException | IllegalActionException ignored) {}
        }
    }

    public void disableSubmit(){
        submit.setDisable(true);
        buttonGraphic.setOpacity(0.5);
    }

    public void enableSubmit(){
        submit.setDisable(false);
        buttonGraphic.setOpacity(1);
    }


    @Override
    public void onScreenOpen() {
        GUI.setSceneController(this);
    }

    @Override
    public void onScreenClose() {
    }

    @Override
    public String getSceneName() {
        return "godSelection";
    }
}
