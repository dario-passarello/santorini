package controller;

import model.Game;
import network.Server;
import network.messages.toclient.ExceptionMessage;
import view.RemoteView;

/**
 * This is the abstract class that contains the Common methods of both the TurnController and the GameController
 */
public abstract class StateMachineController {

    protected final Game game;
    protected final Controller controller;

    /**
     * Standard Constructor. Given a controller and a game, it creates an instance of StateMachineController
     * @param game The instance of the game
     * @param controller The instance of the general controller
     */
    StateMachineController(Game game, Controller controller) {
        this.game = game;
        this.controller = controller;
    }

    /**
     * This method is called whenever a method of the controller is called, but that method is not compatible with the current State
     * @param remoteview The client caller
     */
    protected abstract void handleStateError(RemoteView remoteview);

    /**
     * This method is called whenever a method of the controller launches an exception
     * @param remoteview The client who called the method
     * @param exception The exception generated
     */
    protected void handleExceptionError(RemoteView remoteview, Exception exception){
        //controller.sendMessage(remoteview, new ExceptionMessage(exception));
        Server.logger.warning(exception.getClass().getName() + "IN STATE MACHINE\n" +
                "PLAYER :" + remoteview.getPlayerName() + "\n" + exception.getMessage());

    }
}
