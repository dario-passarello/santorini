package view.GUI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Board;
import model.Builder;
import model.Player;
import model.Turn;
import utils.Coordinate;
import view.*;
import view.screens.BoardScreen;

import java.net.URL;
import java.util.List;

public class GUIBoard extends BoardScreen implements GUIController {

    @FXML GridPane keyboard;
    @FXML GridPane highlight;
    @FXML GridPane buildings;
    @FXML GridPane domes;
    @FXML GridPane builders;
    @FXML VBox gods;
    @FXML Label turnLabel;
    @FXML Button endPhase;
    @FXML Button toggleSpecialPower;
    @FXML Button resetPhase;

    public static final float SQUARE_SIZE = 80;
    public List<String> color = List.of("green", "red", "blue");


    public GUIBoard(ViewManager view, String activePlayer, List<Player> players, List<Coordinate> preHighCoords) {
        super(view, activePlayer, players, preHighCoords);
    }

    public void initialize(){
        GUI.getStage().setMaxWidth(1280);
        GUI.getStage().setMinWidth(1280);
        GUI.getStage().setMaxHeight(720);
        GUI.getStage().setMinHeight(720);

        updateTurnLabel();

        //Creating god images and player names
        for(Player p : getPlayers()){
            HBox hBox = new HBox();                             //HBox will contain the image of god + name of player
            hBox.setSpacing(10);
            StackPane stackPane = new StackPane();
            stackPane.setMinHeight(200);


            ImageView godImage = new ImageView();
            Image img = AssetLoader.getGodAssetsBundle(p.getGod().getName()).loadGodFigureImage();
            godImage.setImage(img);
            godImage.setPreserveRatio(true);
            godImage.setFitHeight(150);

            Button description = new Button();
            description.setPrefSize(200, 200);
            description.setOpacity(0);
            Tooltip tooltip = new Tooltip();
            tooltip.setText(p.getGod().getName().toUpperCase() + ":\n" + AssetLoader.getGodAssetsBundle(p.getGod().getName()).getDescription());
            description.setTooltip(tooltip);

            ImageView podium = new ImageView(String.valueOf(getClass().getResource("/assets/podium.png")));
            podium.setPreserveRatio(true);
            podium.setFitHeight(80);


            stackPane.getChildren().addAll(podium, godImage, description);
            StackPane.setAlignment(podium, Pos.BOTTOM_CENTER);
            StackPane.setAlignment(godImage, Pos.TOP_CENTER);
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
                button.setOpacity(0);
                keyboard.add(button, j, i, 1, 1);

                //highlight layer
                mark = new ImageView();
                mark.setImage(new Image(String.valueOf(getClass().getResource("/assets/highlight.gif"))));
                mark.setPreserveRatio(true);
                mark.setFitHeight(SQUARE_SIZE);
                mark.setVisible(false);
                highlight.add(mark, j, i, 1, 1);

                GridPane.setHalignment(mark, HPos.CENTER);
                GridPane.setValignment(mark, VPos.CENTER);

                //buildings layer
                building = new ImageView();
                building.setPreserveRatio(true);
                building.setFitHeight(SQUARE_SIZE);
                buildings.add(building, j, i, 1, 1);

                //domes layer
                dome = new ImageView();
                dome.setImage(new Image(String.valueOf(getClass().getResource("/assets/dome.png"))));
                dome.setPreserveRatio(true);
                dome.setFitHeight(SQUARE_SIZE/2);
                dome.setVisible(false);
                domes.add(dome, j, i, 1, 1);

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
                        updateButtons();
                        updateBuilders();
                        highlight(getHighlightedCoordinates());
                    } catch (IllegalActionException | IllegalValueException | ActivityNotAllowedException ignored) {}
                });

            }
        }
        highlight(getHighlightedCoordinates());
    }



    public void updateTurnLabel(){
        if(getThisPlayerName().equals(getActivePlayer())){
            turnLabel.setText("IT'S YOUR TURN!");
        } else {
            turnLabel.setText("IT'S " + getActivePlayer() +"'S TURN!");
        }
    }


    public void highlight(List<Coordinate> allowedTiles){
        //Clear previous highlight
        for(int i = 0; i < Board.BOARD_SIZE; i++){
            for(int j = 0; j < Board.BOARD_SIZE; j++){
                GUI.getNodeFromGridPane(highlight, j, i).setVisible(false);
            }
        }
        if(isActiveScreen()) {
            for (Coordinate c : allowedTiles) {
                GUI.getNodeFromGridPane(highlight, c.getY(), c.getX()).setVisible(true);
            }
        }
    }




    public void endPhaseListener() {
        try {
            super.endPhase();
            updateButtons();
            highlight(getHighlightedCoordinates());
        } catch (IllegalActionException ignored){}
    }

    public void resetPhaseListener(){
        try {
            super.resetPhase();
            updateButtons();
            highlight(getHighlightedCoordinates());
        } catch (IllegalActionException ignored){}
    }

    public void toggleSpecialPowerListener(){
        try {
            super.toggleSpecialPower();
            updateButtons();
            highlight(getHighlightedCoordinates());
        } catch (IllegalActionException ignored){}
    }


    public void updateButtons(){
        //Update special power button
        if(specialPowerAvailable()){
            toggleSpecialPower.setDisable(false);
            toggleSpecialPower.setOpacity(1);
        } else {
            toggleSpecialPower.setDisable(true);
            toggleSpecialPower.setOpacity(0.5);
        }

        //Update end phase button
        if(endPhaseAvailable()){
            endPhase.setDisable(false);
            endPhase.setOpacity(1);
        } else {
            endPhase.setDisable(true);
            endPhase.setOpacity(0.5);
        }

        //Update reset phase
        if(getSelectedBuilder() != null && resetPhaseAvailable()){
            resetPhase.setDisable(false);
            resetPhase.setOpacity(1);
        } else {
            resetPhase.setDisable(true);
            resetPhase.setOpacity(0.5);
        }

    }

    @Override
    public void receiveAllowedSquares(Builder builder, List<Coordinate> allowedTiles, boolean specialPower){
        super.receiveAllowedSquares(builder, allowedTiles, specialPower);
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

    @Override
    public void receiveBuildersPositions(List<Builder> builders){
        super.receiveBuildersPositions(builders);
        Platform.runLater(this::updateBuilders);
        Platform.runLater(this::updateTurnLabel);
    }



    public void updateBuilders(){
        //Clear board
        for(int i = 0; i < Board.BOARD_SIZE; i++){
            for(int j = 0; j < Board.BOARD_SIZE; j++){
                ((ImageView) GUI.getNodeFromGridPane(this.builders, j, i)).setImage(null);
            }
        }

        for(Builder b : getCurrBuilders()){
            Coordinate newC = b.getSquare().getCoordinate();
            URL url = getClass().getResource("/assets/"+ color.get(getPlayers().indexOf(b.getOwner())) + "_pawn.png");
            Image img = new Image(String.valueOf(url));
            ((ImageView) GUI.getNodeFromGridPane(this.builders, newC.getY(), newC.getX())).setImage(img);
        }
    }


    @Override
    public void receiveTurnState(Turn.State state, Player player) {
        super.receiveTurnState(state, player);
        //Platform.runLater(this::updateTurnLabel);
    }

    @Override
    public void receiveUpdateDone() {
        super.receiveUpdateDone();
        Platform.runLater(this::updateTurnLabel);

        highlight(getHighlightedCoordinates());
        Platform.runLater(this::updateBuilders);
        Platform.runLater(this::updateButtons);
    }







    @Override
    public void onScreenOpen() {
        Platform.runLater(() ->GUI.setSceneController(this));
    }

    @Override
    public void onScreenClose() {

    }

    @Override
    public String getSceneName() {
        return "board";
    }

}
