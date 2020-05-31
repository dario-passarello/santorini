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

    public static final float SQUARE_SIZE = 50;


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
        ImageView mark, building, dome, builder;
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
                mark.setPreserveRatio(true);
                mark.setFitHeight(SQUARE_SIZE);
                mark.setVisible(false);
                highlight.add(mark, j, i, 1, 1);

                //buildings layer
                building = new ImageView();
                building.setPreserveRatio(true);
                building.setFitHeight(SQUARE_SIZE);
                buildings.add(building, j, i, 1, 1);

                //domes layer
                dome = new ImageView();
                dome.setImage(new Image(String.valueOf(getClass().getResource("/assets/dome.png"))));
                dome.setPreserveRatio(true);
                dome.setFitHeight(SQUARE_SIZE);
                dome.setVisible(false);
                domes.add(domes, j, i, 1, 1);

                //builders layer
                builder = new ImageView();
                builder.setPreserveRatio(true);
                builder.setFitHeight(SQUARE_SIZE/2);
                builders.add(builder, j, i, 1, 1);

                //Setting board listeners
                int finalI = i;
                int finalJ = j;
                button.setOnMouseClicked((event) -> {
                    try {
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
        highlight(getHighlightedCoordinates());
    }

    @Override
    public void receiveBoard(Board board) {
        super.receiveBoard(board);

        Board b = getBoard();
        for(int i = 0; i < Board.BOARD_SIZE; i++){
            for(int j = 0; j < Board.BOARD_SIZE; j++){
                int level = b.squareAt(i,j).getBuildLevel();
                boolean dome = b.squareAt(i,j).isDomed();

                if(level == 0){
                    ((ImageView) GUI.getNodeFromGridPane(buildings, j, i)).setImage(null);
                } else {
                    ((ImageView) GUI.getNodeFromGridPane(buildings, j, i)).setImage(new Image(String.valueOf(getClass().getResource("/assets/build_"+level+".png"))));
                }

                GUI.getNodeFromGridPane(domes, j, i).setVisible(dome);
            }
        }

    }

    /*
    @Override
    public void receiveBuildersPositions(List<Builder> builders){

        List<Builder> oldBuilders, newBuilders;
        oldBuilders = getAllBuilders();
        super.receiveBuildersPositions(builders);
        newBuilders = getAllBuilders();

        for(Builder newB : newBuilders){
            for(Builder oldB : oldBuilders){
                if(newB.equals(oldB)) break;
                if(newB.getOwner().equals(oldB.getOwner()) && newB.getId() == oldB.getId()){
                    Coordinate oldC = oldB.getSquare().getCoordinate();
                    Coordinate newC = newB.getSquare().getCoordinate();
                    ((ImageView) GUI.getNodeFromGridPane(this.builders, oldC.getY(), oldC.getX())).setImage(null);
                    ((ImageView) GUI.getNodeFromGridPane(this.builders, newC.getY(), newC.getX())).setImage(null TODO load builder image);
                }
            }
        }
    }

     */





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
