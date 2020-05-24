package view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.Player;
import view.ViewManager;
import view.screens.PickGodScreen;

import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GUIPickGod extends PickGodScreen implements GUIController {

    @FXML HBox godGraphics;
    @FXML HBox token;
    @FXML HBox keyboard;

    private ImageView godImage;
    private ImageView tokenImage;
    private Button button;
    private Image img;
    private URL url;

    public GUIPickGod(ViewManager view, String firstActivePlayer, List<String> availableGods) {
        super(view, firstActivePlayer, availableGods);
    }

    public void initialize(){
        List<Integer> numGod = getAllGodToChoose().stream().map(this::godToNumber).collect(Collectors.toList());
        for(Integer i : numGod){
            //Creating god images
            godImage = new ImageView();
            godImage.setPreserveRatio(true);
            godImage.setFitHeight(282);
            url = getClass().getResource("gods/"+ i +".png");
            img = new Image(String.valueOf(url));
            godImage.setImage(img);
            godGraphics.getChildren().add(godImage);

            //Creating keyboard
            button = new Button(i.toString());
            button.setPrefSize(168,282);
            button.setOpacity(0);
            keyboard.getChildren().add(button);

            //Creating token
            tokenImage = new ImageView();
            tokenImage.setPreserveRatio(true);
            tokenImage.setFitHeight(282);
            url = getClass().getResource("assets/taken.png");
            img = new Image(String.valueOf(url));
            tokenImage.setImage(img);
            if(!getGodsRemaining().stream().map(this::godToNumber).collect(Collectors.toList()).contains(i)){
                button.setDisable(true);
                tokenImage.setOpacity(0.5);
            } else {
                tokenImage.setOpacity(0);
            }
            token.getChildren().add(tokenImage);
        }

    }

    public Integer godToNumber(String god){
        int count = 0;
        Scanner scan = new Scanner(getClass().getResourceAsStream("assets/numGod.txt"));
        while(!scan.nextLine().equals(god)){
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
