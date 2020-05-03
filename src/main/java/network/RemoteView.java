package network;

import controller.GameController;
import utils.Message;

public class RemoteView {

    private Connection connection;
    private GameController controller;
    private String Nickname;

    public RemoteView(Connection connection){
        this.connection = connection;
    }

    /**
     * Method that calls the controller to handle the data received
     * @param message
     */
    public void handleMessage(Message message){
        controller.processMessage(message);
    }

    /**
     * Method that sends the message to the corresponding client
     * @param message
     */
    public void sendMessage(Message message){
        connection.send(message);
    }
}
