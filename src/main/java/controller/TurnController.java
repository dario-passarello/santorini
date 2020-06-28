package controller;

import model.Game;
import model.Turn;
import network.Server;
import network.messages.toclient.ExceptionMessage;
import network.messages.toclient.StateErrorMessage;
import utils.Coordinate;
import view.RemoteView;

/**
 * This class represents the Controller of the Turn. It handles all the possible interactions between the view and the Model
 */
public class TurnController extends StateMachineController{

    /**
     * Standard Constructor. Given a Controller and a Game, it creates an instance of TurnController
     * @param model The intended game
     * @param controller The general controller
     */
    TurnController(Game model, Controller controller){
        super(model,controller);
    }

    /**
     * This method is called whenever, at the start of the turn, the player chooses the first coordinate as goal for the first action.
     * The first action could either be a normal move or a special action (the latter available onl if the player has a specific god)
     * @param caller The player from which the message comes from
     * @param builderID The builder from which the player has decided to execute the action
     * @param coordinate The coordinate chosen as the goal of the action
     * @param specialPower The parameter that specifies if the action is a regular one or a special one
     */
    public synchronized void firstMove(RemoteView caller, int builderID, Coordinate coordinate, boolean specialPower){
        boolean callerCorrect = checkTurnCorrectness(caller);
        if(callerCorrect) {
            try {
                boolean allowedAction = game.getCurrentTurn().firstSelection(builderID, coordinate, specialPower);
                if (!allowedAction){
                    handleStateError(caller);
                }
            }
            catch(IllegalArgumentException | IndexOutOfBoundsException exception){
                handleExceptionError(caller, exception);
            }
        }
    }

    /**
     * This method is called when an action requires the selection of a coordinate to be executed.
     * The model contains the logic responsible for detecting which action can be carried out by selecting a coordinate, depending on the current TurnState
     * @param caller The player from which the message comes from
     * @param coordinate The coordinate chosen as the goal of the action
     * @param specialPower The parameter that specifies if the player has chosen to execute a special action
     */
    public synchronized void selectCoordinate(RemoteView caller, Coordinate coordinate, boolean specialPower){
        boolean callerCorrect = checkTurnCorrectness(caller);
        if(callerCorrect) {
            try {
                boolean allowedAction = game.getCurrentTurn().selectCoordinate(coordinate, specialPower);
                if (!allowedAction){
                    handleStateError(caller);
                }
            }
            catch(IllegalArgumentException exception){
                handleExceptionError(caller, exception);
            }
        }
    }

    /**
     * This method is called whenever the player has an additional optional action and decides not to carry it out
     * @param caller The player from which the message comes from
     */
    public synchronized void endPhase(RemoteView caller){
        boolean callerCorrect = checkTurnCorrectness(caller);
        if(callerCorrect) {
            try {
                boolean allowedAction = game.getCurrentTurn().endPhase();
                if (!allowedAction){
                    handleStateError(caller);
                }
            }
            catch(IllegalStateException exception){
                handleExceptionError(caller, exception);
            }
        }
    }


    @Override
    protected void handleStateError(RemoteView remoteview) {
        Server.logger.warning("TURN STATE ERROR: Current state is" + game.getStateIdentifier());
        //Turn.State state = game.getCurrentTurn().getStateID();
        //controller.sendMessage(remoteview, new StateErrorMessage<>(state));
    }

    /**
     * This method checks if the client that calls a controller method has the authority to do it or not
     * @param caller The client from which the message comes from
     * @return True if the client can call a controller method or not.
     */
    private boolean checkTurnCorrectness(RemoteView caller){
        if(game.getGameState() != game.turnState) {
            controller.sendMessage(caller, new StateErrorMessage<>(game.getGameState().getStateIdentifier()));
            return false;
        }
        String callerUsername = caller.getPlayerName();
        String effectiveUsername = game.getCurrentTurn().getCurrentPlayer().getName();
        if(!callerUsername.equals(effectiveUsername)){
            controller.sendMessage(caller, new ExceptionMessage(new IllegalCallerException()));
            return false;
        }
        return true;
    }

}
