package controller;

import model.Game;
import network.RemoteView;
import utils.CoordinateMessage;
import utils.Message;

import java.util.List;
import java.util.Set;

public class Controller {

    private Game model;
    private GameController gameController;
    private TurnController turnController;
    private List<RemoteView> remoteViews;


    public void processMessage(RemoteView remoteview, Message message){



        // Does not process any message from a non-listed client
        if(!remoteViews.contains(remoteview)) return;
        else {
            switch (message.getMessageType()) {
                case GOD_LIST:
                    gameController.submitGodList(remoteview, (Set<String>) message.getContent());
                    break;
                case GOD_PICK:
                    gameController.pickGod(remoteview, (String) message.getContent());
                    break;
                case BUILDER_PLACEMENT:
                    gameController.placeBuilder(remoteview, (CoordinateMessage) message.getContent());
                    break;
                case FIRST_ACTION:
                    turnController.firstMove(remoteview, (CoordinateMessage) message.getContent());
                case COORDINATE:
                    turnController.selectCoordinate(remoteview, (CoordinateMessage) message.getContent());
                case END_PHASE:
                    turnController.endPhase(remoteview);


            }
        }
    }


    public void sendMessage(RemoteView remoteView, Message message){

    }

    public Controller(Game model, List<RemoteView> remoteviews){
        this.model = model;
        this.remoteViews = remoteviews;
        this.gameController = new GameController(model, this);
        this.turnController = new TurnController(model, this);
    }


    //Only for Testing
    public TurnController getTurnController(){
        return this.turnController;
    }

    public GameController getGameController(){
        return this.gameController;
    }
}
