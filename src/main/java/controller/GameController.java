package controller;

import model.Game;
import network.Server;
import network.messages.toclient.StateErrorMessage;
import utils.Coordinate;
import view.RemoteView;

import java.util.NoSuchElementException;
import java.util.Set;

/**
 * This class represent the Controller of the Game. It is responsible for advancing the flow of the Game
 */
public class GameController extends StateMachineController{


    /**
     * Standard Constructor. Given a controller and a game, it creates an instance of GameController
     * @param model The intended Game
     * @param controller The general Controller
     */
    GameController(Game model, Controller controller){
        super(model,controller);
    }

    /**
     * This method advanced the FSM when the game is at the GodSelection Phase and the list of gods is selected
     * @param caller The player calling this method
     * @param godList The list of gods selected by the player
     */
    public synchronized void submitGodList(RemoteView caller, Set<String> godList){
        if(!caller.getPlayerName().equals(game.getFirstPlayer().getName())) { //The first player should choose the gods in game
            handleExceptionError(caller, new IllegalCallerException("Not the first player!"));
            return;
        }
        try {
            boolean stateAllowed = game.submitGodList(godList);
            if (!stateAllowed){
                handleStateError(caller);
            }
        }
        catch(IllegalArgumentException exception){
            handleExceptionError(caller, exception);
        }

    }

    /**
     * This method advances the FSM when the game is at the PlaceBuilder Phase and the player has chosen where to build the builder
     * @param caller The player calling this method
     * @param choice The coordinate chosen by the player
     */
    public synchronized void placeBuilder(RemoteView caller, Coordinate choice) {
        try {
            boolean stateAllowed = game.selectCoordinate(caller.getPlayerName(), choice);
            if (!stateAllowed){
                handleStateError(caller);
            }
        }
        catch(IllegalArgumentException exception){
            handleExceptionError(caller, exception);
        }
    }

    /**
     * This method advances the FSM when the game is at the PickGod phase and the player which god to pick
     * @param caller The player calling this method
     * @param godName The coordinate chosen by the player
     */
    public synchronized void pickGod(RemoteView caller, String godName) {
        try {
            boolean stateAllowed = game.pickGod(caller.getPlayerName(), godName);
            if (!stateAllowed){
                handleStateError(caller);
            }
        }
        catch(IllegalArgumentException exception){
            handleExceptionError(caller, exception);
        }
    }

    /**
     * This method deletes a player when a client disconnects
     * @param caller The client that has disconnected
     */
    public synchronized void disconnectPlayer(RemoteView caller){
        try{
            game.unregisterAllTurnObservers(caller);
            game.unregisterObserver(caller);
            game.removePlayer(caller.getPlayerName(),true);
        }
        catch (IllegalArgumentException | NoSuchElementException exception) {
            handleExceptionError(caller, exception);
        }
    }



    @Override
    protected void handleStateError(RemoteView remoteview){
        Server.logger.warning("GAME STATE ERROR: Current state is " + game.getStateIdentifier());
        //Game.State state = game.getStateIdentifier();
        //controller.sendMessage(remoteview, new StateErrorMessage<Game.State>(state));
    }
}
