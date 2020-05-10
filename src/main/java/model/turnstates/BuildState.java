package model.turnstates;

import model.*;
import utils.Coordinate;

import java.util.List;

public class BuildState implements TurnState {

    private final Turn turn;
    private final Game game;
    private final boolean optional;

    public BuildState(Turn turn, Game game, boolean optional){
        this.turn = turn;
        this.game = game;
        this.optional = optional;
    }

    public boolean firstSelection(int builderID, Coordinate c, boolean specialPower) {
        return false;
    }

    public boolean selectCoordinate(Coordinate coordinate, boolean specialPower) {
        List<Square> buildableSquares;
        Builder activeBuilder = turn.getActiveBuilder();
        Square buildSquare;
        boolean canBuildAgain = false;
        if(!Board.checkValidCoordinate(coordinate)) {
            throw new IllegalArgumentException(ErrorMessage.COORDINATE_NOT_VALID);
        }
        if(specialPower && (!turn.getCurrentPlayer().getGod().hasSpecialBuildPower() || optional)) {
            throw new IllegalArgumentException(ErrorMessage.NO_SPECIAL_POWER);
        }
        buildableSquares = activeBuilder.getBuildableNeighborhood();
        buildSquare = game.getBoard().squareAt(coordinate);
        if(!buildableSquares.contains(buildSquare)) {
            throw new IllegalArgumentException(ErrorMessage.ILLEGAL_MOVE);
        }
        //If the action is valid and will be executed this point is reached
        if(specialPower) {
            activeBuilder.buildDome(buildSquare);
            turn.setTurnState(turn.endTurnState);
        } else {
            canBuildAgain = activeBuilder.build(buildSquare);
            if(canBuildAgain) {
                turn.setTurnState(turn.additionalBuildState);
                //Send the buildable square for the additional build phase
                turn.notifyObservers(obs -> {
                    obs.receiveAllowedSquares(activeBuilder,activeBuilder.getBuildableCoordinates());
                } );
            } else {
                turn.setTurnState(turn.endTurnState);
            }
        }
        turn.notifyObservers(obs -> {
            obs.receiveBoard(new Board(game.getBoard()));
            obs.receiveUpdateDone();
        });
        //Check the special winning condition (after the build)
        turn.getCurrentPlayer().getGod().checkSpecialWinCondition().ifPresent(game::setWinner);
        return true;
    }

    public boolean endPhase() {
        if(!optional)
            throw new IllegalStateException(ErrorMessage.NOT_OPTIONAL_STATE); //The state is not optional, could not be skipped
        turn.setTurnState(turn.endTurnState);
        turn.notifyObservers(TurnObserver::receiveUpdateDone);
        return true;
    }

    public Turn.State getStateID() {
        return Turn.State.BUILD;
    }
}
