package controller;

import model.Game;
import network.Server;
import network.messages.toclient.ExceptionMessage;
import view.RemoteView;

public abstract class StateMachineController {

    protected final Game game;
    protected final Controller controller;

    StateMachineController(Game game, Controller controller) {
        this.game = game;
        this.controller = controller;
    }

    protected abstract void handleStateError(RemoteView remoteview);

    protected void handleExceptionError(RemoteView remoteview, Exception exception){
        //controller.sendMessage(remoteview, new ExceptionMessage(exception));
        Server.logger.warning(exception.getClass().getName() + "IN STATE MACHINE\n" +
                "PLAYER :" + remoteview.getPlayerName() + "\n" + exception.getMessage());
        exception.printStackTrace();

    }
}
