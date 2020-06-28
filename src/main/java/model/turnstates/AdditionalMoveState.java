package model.turnstates;

import model.*;
import utils.Coordinate;

import java.util.List;

/**
 * This class represents the moment where the Player has a either an optional additional move, or a move state that is
 * forced to be bound to a specific builder
 */
public class AdditionalMoveState implements TurnState {

    private final Turn turn;
    private final Game game;
    private final boolean optional;

    /**
     * The constructor of the class
     * @param turn The reference of the current turn
     * @param game The reference of the current game
     * @param optional The parameter that specifies if this state is an optional state or not
     */
    public AdditionalMoveState(Turn turn, Game game, boolean optional){
        this.turn = turn;
        this.game = game;
        this.optional = optional;
    }

    @Override
    public boolean firstSelection(int builderID, Coordinate c, boolean specialPower) {
        return false;
    }

    /**
     * This method is called when the player chooses where to move the builder in this state
     * @param coordinate The coordinate target
     * @param specialPower The parameter which represent if the action is a special one or not
     * @return True if the method is called from a legit state. False otherwise
     */
    @Override
    public boolean selectCoordinate(Coordinate coordinate, boolean specialPower) {
        List<Square> walkableSquares; //Squares where the builder would be moved
        Square moveSquare; //The square where the active builder would be moved
        Square currentSquare; //The square were the builder is
        Builder activeBuilder; //The builder that could be moved because made the first move, at the start of the turn
        boolean canMoveAgain;
        if(!Board.checkValidCoordinate(coordinate)) {
            throw new IllegalArgumentException(ErrorMessage.COORDINATE_NOT_VALID);
        }
        if(specialPower) {
            throw new IllegalArgumentException(ErrorMessage.NO_SPECIAL_POWER);
        }
        activeBuilder = turn.getActiveBuilder();
        walkableSquares = turn.getActiveBuilder().getWalkableNeighborhood();
        currentSquare = turn.getActiveBuilder().getSquare();
        moveSquare = game.getBoard().squareAt(coordinate);
        if(!walkableSquares.contains(moveSquare)) {
            throw new IllegalArgumentException(ErrorMessage.ILLEGAL_MOVE);
        }
        //This point is reached if the move is valid and will be executed
        canMoveAgain = turn.getActiveBuilder().move(moveSquare);
        if(canMoveAgain) {
            turn.setTurnState(turn.additionalMoveState);
            turn.notifyObservers(obs -> {
                obs.receiveBuildersPositions(game.getAllBuilders());
                obs.receiveAllowedSquares(activeBuilder, activeBuilder.getWalkableCoordinates(), false);
                obs.receiveUpdateDone();
            });
        } else {
            turn.setTurnState(turn.buildState);
            turn.notifyObservers(obs -> {
                obs.receiveBuildersPositions(game.getAllBuilders());
                obs.receiveAllowedSquares(activeBuilder, activeBuilder.getBuildableCoordinates(), false);
                obs.receiveUpdateDone();
            });
        }
        turn.getCurrentPlayer().getGod()
                .checkWinCondition(currentSquare,activeBuilder)
                .ifPresent(game::setWinner);
        //Check the special winning condition (after the build)

        return true;
    }

    @Override
    public boolean endPhase() {
        if(!optional)
            throw new IllegalStateException(ErrorMessage.NOT_OPTIONAL_STATE);
        Builder activeBuilder = turn.getActiveBuilder();
        turn.setTurnState(turn.buildState);
        turn.notifyObservers(obs -> {
            obs.receiveAllowedSquares(activeBuilder, activeBuilder.getBuildableCoordinates(), false);
            obs.receiveUpdateDone();
        });
        return true;
    }

    @Override
    public Turn.State getStateID() {
        return optional ? Turn.State.ADDITIONAL_MOVE : Turn.State.SPECIAL_MOVE;
    }
}
