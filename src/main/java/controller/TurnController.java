package controller;



import model.Game;
import model.Turn;
import network.RemoteView;
import utils.CoordinateMessage;
import utils.Message;
import utils.MessageType;

import java.util.List;

public class TurnController {

    private Game model;
    private Controller controller;


    //TODO Catch IllegalStateException somewhere

    public void firstMove(RemoteView remoteView, CoordinateMessage choice){
        if(!correctTurnPlayer(remoteView)) return;
        try {
            if (!model.getCurrentTurn().firstSelection(choice.getBuilder(), choice.getCoordinate(), choice.getSpecialPower())) {
                stateError(remoteView);
            }
        }
        catch(IllegalArgumentException exception){
            exceptionError(remoteView, exception);
           // Testing only - System.out.println("- " +exception.getMessage());
        }

    }


    public void selectCoordinate(RemoteView remoteview, CoordinateMessage choice){
        if(!correctTurnPlayer(remoteview)) return;
        try {
            if (!model.getCurrentTurn().selectCoordinate(choice.getCoordinate(), choice.getSpecialPower())) {
                stateError(remoteview);
            }
        }
        catch(IllegalArgumentException exception){
            exceptionError(remoteview, exception);
            // Testing only - System.out.println("- " +exception.getMessage());
        }

    }

    public void endPhase(RemoteView remoteview){
        if(!correctTurnPlayer(remoteview)) return;
        try {
            if (!model.getCurrentTurn().endPhase()) {
                stateError(remoteview);
            }
        }
        catch(IllegalStateException exception){
            exceptionError(remoteview, exception);
            // Testing only - System.out.println("- " +exception.getMessage());
        }
    }

    public void stateError(RemoteView remoteview){
        Turn.State state = model.getCurrentTurn().getStateID();
        controller.sendMessage(remoteview, new Message(state, MessageType.STATE_ERROR));
    }

    public void exceptionError(RemoteView remoteview, Exception exception){
        controller.sendMessage(remoteview, new Message(exception.getMessage(), MessageType.ILLEGAL_ERROR));
    }

    public boolean correctTurnPlayer(RemoteView remoteView){
        return (remoteView.getNickname().equals(model.getCurrentTurn().getCurrentPlayer().getName()));
    }

    public TurnController(Game model, Controller controller){
        this.model = model;
        this.controller = controller;

    }
}
