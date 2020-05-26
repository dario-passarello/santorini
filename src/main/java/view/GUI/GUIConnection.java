package view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import view.ViewManager;
import view.screens.ConnectionScreen;

public class GUIConnection extends ConnectionScreen implements GUIController{

    //ObservableList numbers = FXCollections.observableArrayList();
    @FXML private TextField ip;
    @FXML private TextField port;
    @FXML private TextField nickname;
    @FXML private ChoiceBox<Integer> choiceBox;

    public GUIConnection(ViewManager view) {
        super(view);
    }

    public void initialize(){
        choiceBox.getItems().addAll(2,3);
        choiceBox.setValue(2);
    }

    public void startButtonPushed(){
        //TODO
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
        return "connection";
    }
}


