package controller;

import model.Game;

import network.messages.Message;
import network.messages.MessageTarget;
import view.ViewManager;
import view.RemoteView;

public class Controller {

    private final Game model;
    private final GameController gameController;
    private final TurnController turnController;


    public Controller(Game model) {
        gameController = new GameController(model, this);
        turnController = new TurnController(model, this);
        this.model = model;
    }

    public GameController game(){
        return this.gameController;
    }

    public TurnController turn(){
        return this.turnController;
    }



    void sendMessage(RemoteView view, Message<? extends MessageTarget> message){
        view.sendMessage(message);
    }

}
