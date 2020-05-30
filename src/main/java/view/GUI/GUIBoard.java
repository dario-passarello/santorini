package view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Board;
import model.Builder;
import model.Player;
import utils.Coordinate;
import view.AssetLoader;
import view.IllegalActionException;
import view.IllegalValueException;
import view.ViewManager;
import view.screens.BoardScreen;

import java.util.List;

public class GUIBoard extends BoardScreen implements GUIController {

    @FXML GridPane keyboard;
    @FXML GridPane highlight;
    @FXML GridPane buildings;
    @FXML GridPane domes;
    @FXML GridPane builders;
    @FXML VBox gods;

    public GUIBoard(ViewManager view, String activePlayer, List<Player> players) {
        super(view, activePlayer, players);
    }

    public void initialize(){
        GUI.getStage().setMaxWidth(1280);
        GUI.getStage().setMinWidth(1280);
        GUI.getStage().setMaxHeight(720);
        GUI.getStage().setMinHeight(720);

        //Creating god images and player names
        for(Player p : getPlayers()){
            HBox hBox = new HBox();                             //HBox will contain the image of god + name of player
            hBox.setSpacing(10);
            StackPane stackPane = new StackPane();

            ImageView godImage = new ImageView();
            Image img = AssetLoader.getGodAssetsBundle(p.getGod().getName()).loadGodFigureImage();
            godImage.setImage(img);
            godImage.setPreserveRatio(true);
            godImage.setFitHeight(100);

            ImageView podium = new ImageView(String.valueOf(getClass().getResource("/assets/podium.png")));
            podium.setPreserveRatio(true);
            podium.setFitHeight(80);

            stackPane.getChildren().add(podium);
            stackPane.getChildren().add(godImage);
            Label name = new Label(p.getName());
            name.setStyle("-fx-font-family: 'Arial Black'; -fx-text-fill: #ffffff");
            hBox.getChildren().addAll(stackPane, name);
            gods.getChildren().add(hBox);
        }


        Button button;
        ImageView mark;
        //Creating layers of the board
        for(int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {

                //keyboard layer
                button = new Button();
                button.setPrefSize(300, 300);
                button.setOpacity(0.3);
                keyboard.add(button, j, i, 1, 1);

                //highlight layer
                mark = new ImageView();
                mark.setImage(new Image(String.valueOf(getClass().getResource("/assets/highlight.gif"))));
                mark.setVisible(false);
                highlight.add(mark, j, i, 1, 1);

                //buildings layer
                //TODO

                //domes layer
                //TODO

                //builders layer
                //TODO

            }
        }

        //Setting listeners
        for(int i = 0; i < Board.BOARD_SIZE; i++){
            for(int j = 0; j < Board.BOARD_SIZE; j++){
                int finalJ = j;
                int finalI = i;
                GUI.getNodeFromGridPane(keyboard, j, i).setOnMouseClicked((event) -> {
                    try {
                        //processBuilderPlacement(new Coordinate(finalI, finalJ));
                        selectSquare(new Coordinate(finalI, finalJ));
                    } catch (IllegalActionException | IllegalValueException ignored) {}
                });
            }
        }
    }
/*
    public void drawBuilder(){
        if(getThisPlayerName().equals(getPlayers().get(0).getName())){
            //TODO
        }
    }
*/

    public void highlight(List<Coordinate> allowedTiles){
        for(Coordinate c : allowedTiles){
            GUI.getNodeFromGridPane(highlight, c.getY(), c.getX()).setVisible(true);
        }
    }



    @Override
    public void receiveAllowedSquares(Builder builder, List<Coordinate> allowedTiles, boolean specialPower){
        super.receiveAllowedSquares(builder, allowedTiles, specialPower);
        highlight(allowedTiles);
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
        return "board";
    }

}
