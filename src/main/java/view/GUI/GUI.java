package view.GUI;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.screens.MenuScreen;

import java.io.IOException;

public class GUI extends Application {

    private static String WINDOW_TITLE = "SANTORINI";
    private static GUI instance;
    private static Scene scene;
    private static Stage stage;
    private static Object object;
    private static GUIController launchScene;




    @Override
    public void start(Stage stage) throws IOException {
        GUI.stage = stage;
        setSceneController(launchScene);
        stage.setTitle(WINDOW_TITLE);
        stage.show();
    }

    public static void setSceneController(GUIController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/"+ controller.getSceneName() + ".fxml"));
        fxmlLoader.setController(controller);
        try{
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    private Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/"+ fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void setLaunchController(GUIController controller){
        launchScene = controller;
    }

}
