package controller;

import model.Game;
import network.messages.toclient.StateErrorMessage;
import utils.Coordinate;
import view.RemoteView;

import java.util.Set;

public class GameController extends StateMachineController{


    GameController(Game model, Controller controller){
        super(model,controller);
    }

    public synchronized void submitGodList(RemoteView caller, Set<String> godList){
        if(!caller.getPlayerName().equals(game.getFirstPlayer().getName())) { //The first player should choose the gods in game
            sendExceptionError(caller, new IllegalCallerException("Not the first player!"));
            return;
        }
        try {
            boolean stateAllowed = game.submitGodList(godList);
            if (!stateAllowed){
                sendStateError(caller);
            }
        }
        catch(IllegalArgumentException exception){
            sendExceptionError(caller, exception);
        }

    }

    public synchronized void placeBuilder(RemoteView caller, Coordinate choice) {
        try {
            boolean stateAllowed = game.selectCoordinate(caller.getPlayerName(), choice);
            if (!stateAllowed){
                sendStateError(caller);
            }
        }
        catch(IllegalArgumentException exception){
            sendExceptionError(caller, exception);
        }
    }

    public synchronized void pickGod(RemoteView caller, String godName) {
        try {
            boolean stateAllowed = game.pickGod(caller.getPlayerName(), godName);
            if (!stateAllowed){
                sendStateError(caller);
            }
        }
        catch(IllegalArgumentException exception){
            sendExceptionError(caller, exception);
        }
    }

    public synchronized void quitGame(RemoteView caller) {
        try {
            boolean stateAllowed = game.quitGame(caller.getPlayerName());
            if(!stateAllowed) {
                sendStateError(caller);
            }
        }
        catch (IllegalArgumentException exception) {
            sendExceptionError(caller, exception);
        }
    }

    @Override
    protected void sendStateError(RemoteView remoteview){
        Game.State state = game.getStateIdentifier();
        controller.sendMessage(remoteview, new StateErrorMessage<Game.State>(state));
    }
}
