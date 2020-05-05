package model.turnstates;

import model.*;
import utils.Coordinate;

import java.util.List;

public class AdditionalMoveState implements TurnState {

    private final Turn turn;
    private final Game game;
    private final boolean optional;

    public AdditionalMoveState(Turn turn, Game game, boolean optional){
        this.turn = turn;
        this.game = game;
        this.optional = optional;
    }

    public boolean firstSelection(Builder b, Coordinate c, boolean specialPower) {
        return false;
    }

    public boolean selectCoordinate(Coordinate c, boolean specialPower) {
        List<Square> walkableSquares; //Squares where the builder would be moved
        Square moveSquare; //The square where the active builder would be moved
        Square currentSquare; //The square were the builder is
        Builder activeBuilder; //The builder that could be moved because made the first move, at the start of the turn
        boolean canMoveAgain;
        if(!Board.checkValidCoordinate(c)) {
            throw new IllegalArgumentException(ErrorMessage.COORDINATE_NOT_VALID);
        }
        if(specialPower) {
            throw new IllegalArgumentException(ErrorMessage.NO_SPECIAL_POWER);
        }
        activeBuilder = turn.getActiveBuilder();
        walkableSquares = turn.getActiveBuilder().getWalkableNeighborhood();
        currentSquare = turn.getActiveBuilder().getSquare();
        moveSquare = game.getBoard().squareAt(c);
        if(!walkableSquares.contains(moveSquare)) {
            throw new IllegalArgumentException(ErrorMessage.ILLEGAL_MOVE);
        }
        //This point is reached if the move is valid and will be executed
        canMoveAgain = turn.getActiveBuilder().move(moveSquare);
        if(canMoveAgain) {
            turn.setTurnState(turn.additionalMoveState);
            turn.notifyObservers(obs -> {
                obs.receiveBuildersPositions(game.getAllBuilders());
                obs.receiveAllowedSquares(activeBuilder, activeBuilder.getWalkableCoordinates());
                obs.receiveUpdateDone();
            });
        } else {
            turn.notifyObservers(obs -> {
                obs.receiveBuildersPositions(game.getAllBuilders());
                obs.receiveAllowedSquares(activeBuilder, activeBuilder.getBuildableCoordinates());
                obs.receiveUpdateDone();
            });
            turn.setTurnState(turn.buildState);
        }
        turn.getCurrentPlayer().getGod()
                .checkWinCondition(currentSquare,activeBuilder)
                .ifPresent(game::setWinner);
        //Check the special winning condition (after the build)

        return true;
    }

    public boolean endPhase() {
        if(!optional)
            throw new IllegalStateException(ErrorMessage.NOT_OPTIONAL_STATE);
        Builder activeBuilder = turn.getActiveBuilder();
        turn.setTurnState(turn.buildState);
        turn.notifyObservers(obs -> {
            obs.receiveAllowedSquares(activeBuilder, activeBuilder.getBuildableCoordinates());
            obs.receiveUpdateDone();
        });
        return true;
    }

    public Turn.State getStateID() {
        return Turn.State.ADDITIONAL_MOVE;
    }
}
