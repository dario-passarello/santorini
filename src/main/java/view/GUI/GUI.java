package view.GUI;

import controller.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.screens.MenuScreen;

import java.io.IOException;

/**
 * manages the GUI start and the change of the current GUI screen
 */
public class GUI extends Application {

    private static String WINDOW_TITLE = "SANTORINI";
    private static Scene currentScene;
    private static Stage stage;
    private static GUIController launchScene;

    static Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }


    @Override
    public void start(Stage stage) throws IOException {
        GUI.stage = stage;
        setSceneController(launchScene);
        stage.setTitle(WINDOW_TITLE);
        stage.show();
    }

    @Override
    public void stop(){
        Platform.exit();
        System.exit(0);
    }

    /**
     * @param controller the controller of the current GUI screen
     */
    public static void setSceneController(GUIController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/"+ controller.getSceneName() + ".fxml"));
        fxmlLoader.setController(controller);
        try{
            currentScene = new Scene(fxmlLoader.load());
            stage.setScene(currentScene);
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * @return currentScene attribute
     */
    public static Scene getCurrentScene(){
        return currentScene;
    }

    /**
     * @return stage attribute
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * @param fxml the name of a .fxml file
     * @return the .fxml loaded
     * @throws IOException if there isn't the desired .fxml file in the path
     */
    private Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/"+ fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * @param controller the controller of the current GUI scene
     */
    public static void setLaunchController(GUIController controller){
        launchScene = controller;
    }

}
