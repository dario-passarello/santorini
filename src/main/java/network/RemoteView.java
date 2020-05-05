package network;

import controller.Controller;
import utils.Message;

public class RemoteView {


    private Controller controller;
    private String username;

    public RemoteView(String username){
        this.username = username;
    }

    /**
     * Method that calls the controller to handle the data received
     * @param message
     */
    public void handleMessage(Message message){
        controller.processMessage(this, message);
    }

    /**
     * Method that sends the message to the corresponding client
     * @param message
     */
    public void sendMessage(Message message){

    }

    public String getNickname(){
        return this.username;
    }
}
