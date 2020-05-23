package view.GUI;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import view.IllegalActionException;
import view.IllegalValueException;
import view.ViewManager;
import view.screens.GodSelectionScreen;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class GUISelectionGod extends GodSelectionScreen {

    @FXML GridPane keyboard;
    @FXML GridPane godGraphics;
    @FXML GridPane selected;
    @FXML ImageView bigCover;
    @FXML ImageView buttonGraphic;
    @FXML Text description;
    @FXML Button submit;
    @FXML Label wait;

    private Button button;
    private ImageView godImage;
    private Image img;
    private ImageView token;


    public GUISelectionGod(ViewManager view, String activePlayer) {
        super(view, activePlayer);
    }


    public void initialize(){

        Integer numGod = 0;
        bigCover.setPreserveRatio(true);
        bigCover.setFitHeight(235);
        description.setStyle("-fx-font-size: 12");
        displayDefault();

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 5; j++) {
                //Creating buttons
                button = new Button(numGod.toString());
                button.setMaxSize(200,200);
                button.setOpacity(0);
                keyboard.add(button, j, i, 1, 1);

                //Creating god images
                godImage = new ImageView();
                URL url = getClass().getResource("gods/"+numGod+".png");
                img = new Image(String.valueOf(url));
                godImage.setImage(img);
                godImage.setPreserveRatio(true);
                godImage.setFitHeight(100);
                godGraphics.add(godImage, j, i, 1, 1);

                //Creating ImageViews for token
                token = new ImageView();
                selected.add(token, j, i, 1, 1);


                //Setting listeners
                Integer finalNumGod = numGod;
                button.setOnMouseEntered((event) -> {
                    try {
                        showGod(new Image(String.valueOf(url)), finalNumGod);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                });
                button.setOnMouseExited((event -> displayDefault()));

                //Players distinction
                if(isActiveScreen()){
                    button.setOnMouseClicked((event) -> takeClickedGod(finalNumGod));
                    submit.setOnMouseClicked((event) -> {
                        try {
                            submitGodList();
                        } catch (IllegalActionException e) {
                            e.printStackTrace();
                        }
                    });
                } else{
                    buttonGraphic.setOpacity(0);
                    wait.setText("Another player is choosing the gods!");
                }

                numGod++;
            }
        }
    }

    public void showGod(Image img, Integer numGod) throws FileNotFoundException {
        displayHoveredGod(img);
        showDescription(numGod);
    }

    public void displayDefault(){
        URL url = getClass().getResource("assets/placeholder.png");
        bigCover.setImage(new Image(String.valueOf(url)));
        description.setText("Select a god");
    }

    public void displayHoveredGod(Image img){
        bigCover.setImage(img);
    }

    public void showDescription(Integer numGod) {
        Scanner scan = new Scanner(getClass().getResourceAsStream("descriptions/d"+numGod+".txt"));
        String description = "";
        while(scan.hasNextLine()){
            description = description.concat(scan.nextLine()+"\n");
        }
        scan.close();
        this.description.setText(description);
    }

    public void takeClickedGod(Integer numGod){
        ImageView selectedGod = (ImageView) getNodeFromGridPane(selected, numGod%5, numGod/5);
        assert selectedGod != null;
        if(!isGodChosen(numberToGod(numGod))) {
            try{
                addGod(numberToGod(numGod));
                URL url = getClass().getResource("assets/token.png");
                selectedGod.setImage(new Image(String.valueOf(url)));
                selectedGod.setPreserveRatio(true);
                selectedGod.setFitHeight(100);
                selectedGod.setOpacity(0.5);
                if(readyToSubmit()) enableSubmit();
            } catch (IllegalValueException | IllegalActionException e) {
                e.printStackTrace();
            }
        } else {
            try {
                removeGod(numberToGod(numGod));
                selectedGod.setImage(null);
                disableSubmit();
            } catch (IllegalValueException | IllegalActionException e) {
                e.printStackTrace();
            }
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

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    public String numberToGod(Integer i){
        int count = 0;
        Scanner scan = new Scanner(getClass().getResourceAsStream("assets/numGod.txt"));
        String god;
        while(count < i){
            scan.nextLine();
            count++;
        }
        god = scan.nextLine();
        scan.close();
        return god;
    }

    public Integer godToNumber(String god){
        int count = 0;
        Scanner scan = new Scanner(getClass().getResourceAsStream("assets/numGod.txt"));
        while(scan.nextLine() != god){
            count++;
        }
        return count;
    }

    @Override
    public void onScreenOpen() {
    }

    @Override
    public void onScreenClose() {
    }
}
