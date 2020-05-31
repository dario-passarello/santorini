package view.GUI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.ClientErrorMessages;
import view.IllegalActionException;
import view.IllegalValueException;
import view.ViewManager;
import view.screens.ConnectionScreen;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class GUIConnection extends ConnectionScreen implements GUIController{

    //ObservableList numbers = FXCollections.observableArrayList();
    @FXML private transient TextField ip;
    @FXML private transient TextField port;
    @FXML private transient TextField nickname;
    @FXML private transient ChoiceBox<Integer> choiceBox;
    @FXML private transient Text warning;
    @FXML private transient HBox loginFields;
    @FXML private transient VBox loading;

    public GUIConnection(ViewManager view) {
        super(view);
    }

    public void initialize(){
        GUI.getStage().setMaxWidth(1280);
        GUI.getStage().setMinWidth(1280);
        GUI.getStage().setMaxHeight(720);
        GUI.getStage().setMinHeight(720);
        //Set initial values loaded from file
        ip.setText(getFieldIp());
        port.setText(String.valueOf(getFieldPort()));
        nickname.setText(getFieldUsername());
        choiceBox.getItems().addAll(2,3);
        choiceBox.setValue(getFieldNumberOfPlayers());
        warning.setFont(Font.font ("Verdana", 12));
        warning.setFill(Color.RED);
    }

    public void startButtonPushed(){
        warning.setText("");                            //clear warnings
        try {
            setIP(ip.getText());
        }catch (IllegalValueException e) { warning.setText(warning.getText() + "- You should use a valid IP\n");}
        try {
            setPort(port.getText());
        }catch (IllegalValueException e2) { warning.setText(warning.getText() + "- You should use a valid port\n");}
        try {
            setUsername(nickname.getText());
        }catch (IllegalValueException e) { warning.setText(warning.getText() + "- Your nickname should have from 3 up to 30 characters\n");}
        setNumberOfPlayers(choiceBox.getValue());
        if(readyToConnect()){
            try {
                loginFields.setDisable(true);
                loginFields.setVisible(false);
                Platform.runLater(() -> loading.setVisible(true));
                connect();
            } catch (IllegalActionException | IOException e){};
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
    public String getSceneName() {
        return "connection";
    }
}


