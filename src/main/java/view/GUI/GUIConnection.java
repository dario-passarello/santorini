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

/**
 * This class represents the GUI controller during the connection phase
 */
public class GUIConnection extends ConnectionScreen implements GUIController{

    //ObservableList numbers = FXCollections.observableArrayList();
    @FXML private transient TextField ip;
    @FXML private transient TextField port;
    @FXML private transient TextField nickname;
    @FXML private transient ChoiceBox<Integer> choiceBox;
    @FXML private transient Text warning;
    @FXML private transient HBox loginFields;
    @FXML private transient VBox loading;

    /**
     * GUIConnection constructor
     * @param view the view manager used
     */
    public GUIConnection(ViewManager view) {
        super(view);
    }

    /**
     * initializes the GUIConnection
     */
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

    /**
     * if the player have used valid inputs, it shows a waiting screen and then, when other players are available, brings the game to the GUIGodSelection screen
     */
    public void startButtonPushed(){
        warning.setText("");                            //clear warnings
        boolean flag = true;
        try {
            setIP(ip.getText());
        }catch (IllegalValueException e) {
            warning.setText(warning.getText() + "- You should use a valid IP\n");
            flag = false;
        }
        try {
            setPort(port.getText());
        }catch (IllegalValueException e2) { warning.setText(warning.getText() + "- You should use a valid port\n");
            flag = false;
        }
        try {
            setUsername(nickname.getText());
        }catch (IllegalValueException e) { warning.setText(warning.getText() + "- Your nickname should have from 3 up to 30 characters\n");
            flag = false;
        }
        setNumberOfPlayers(choiceBox.getValue());
        if(readyToConnect() && flag){
            loginFields.setDisable(true);
            loginFields.setVisible(false);
            loading.setVisible(true);
            Thread t = new Thread(() ->{
                try {
                    connect();
                } catch (IllegalActionException | IOException e) {
                    loginFields.setDisable(false);
                    loginFields.setVisible(true);
                    loading.setVisible(false);
                    warning.setText(warning.getText() + "- Could not connect to the server");
                }
            });
            t.start();
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


