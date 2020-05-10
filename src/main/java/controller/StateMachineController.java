package controller;

import model.Game;
import network.messages.toclient.ExceptionMessage;
import view.RemoteView;

public abstract class StateMachineController {

    protected final Game game;
    protected final Controller controller;

    StateMachineController(Game game, Controller controller) {
        this.game = game;
        this.controller = controller;
    }

    protected abstract void sendStateError(RemoteView remoteview);

    protected void sendExceptionError(RemoteView remoteview, Exception exception){
        controller.sendMessage(remoteview, new ExceptionMessage(exception));
    }
}
