package controller;

import model.Game;

import network.messages.Message;
import network.messages.MessageTarget;
import view.ViewManager;
import view.RemoteView;

/**
 * This is the general Controller of the entire application. It contains a reference to all the Finite State Machines that handle the flow of the game
 */
public class Controller {

    private final Game model;
    private final GameController gameController;
    private final TurnController turnController;


    /**
     * The constructor of this class. It creates an instance of this, as well as the instances of all the Controllers of the different Finite State Machines in the game
     * @param model An instance of the game
     */
    public Controller(Game model) {
        gameController = new GameController(model, this);
        turnController = new TurnController(model, this);
        this.model = model;
    }

    /**
     * Standard GameController getter
     * @return The current instance of the GameController
     */
    public GameController game(){
        return this.gameController;
    }

    /**
     * Standard TurnController getter
     * @return The current instance of the TurnController
     */
    public TurnController turn(){
        return this.turnController;
    }


    /**
     * This method sends a message to the client, whenever it is required to
     * @param view The client target
     * @param message The message object
     */
    void sendMessage(RemoteView view, Message<? extends MessageTarget> message){
        view.sendMessage(message);
    }

}
