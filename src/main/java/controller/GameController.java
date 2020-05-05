package controller;

import model.Game;
import network.RemoteView;
import utils.CoordinateMessage;
import utils.Message;
import utils.MessageType;
import java.util.Set;

public class GameController {


    private Game model;
    private Controller controller;

    public GameController(Game model, Controller controller){
       this.model = model;
       this.controller = controller;
    }






    public void submitGodList(RemoteView remoteview, Set<String> GodList){
        try {
            if (!model.submitGodList(GodList)){
                stateError(remoteview);
            }
        }
        catch(IllegalArgumentException exception){
            exceptionError(remoteview, exception);
        }

    }

    public void placeBuilder(RemoteView remoteview, CoordinateMessage choice) {
        try {
            if (!model.selectCoordinate(remoteview.getNickname(), choice.getCoordinate())){
                stateError(remoteview);
            }
        }
        catch(IllegalArgumentException exception){
            exceptionError(remoteview, exception);
        }
    }

    public void pickGod(RemoteView remoteview, String godName){
        try {
            if (!model.pickGod(remoteview.getNickname(), godName)){
                stateError(remoteview);
            }
        }
        catch(IllegalArgumentException exception){
            exceptionError(remoteview, exception);
        }
    }

    public void stateError(RemoteView remoteview){
        Game.State state = model.getStateIdentifier();
        controller.sendMessage(remoteview, new Message(state, MessageType.STATE_ERROR));
    }

    public void exceptionError(RemoteView remoteview, Exception exception){
        controller.sendMessage(remoteview, new Message(exception.getMessage(), MessageType.ILLEGAL_ERROR));
    }
}
