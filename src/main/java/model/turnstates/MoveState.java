package model.turnstates;

import model.*;
import model.gods.God;
import model.movebehaviors.NoUpMove;
import utils.Coordinate;

import java.util.List;

public class MoveState implements TurnState {
    private final Turn turn;
    private final Game game;

    public MoveState(Turn turn, Game game){
        this.turn = turn;
        this.game = game;
    }

    public boolean firstSelection(Builder builder, Coordinate coordinate, boolean specialPower) {
        List<Square> squaresAllowed; //Squares where the player could go
        Square currentSquare;   //The square were the active builder is located
        Square actionSquare;    //The square selected from the player
        God playerGod;
        boolean canMoveAgain;
        if(!builder.getOwner().equals(turn.getCurrentPlayer()))
            throw new IllegalArgumentException(ErrorMessage.WRONG_BUILD_OWNER);
        if(!Board.checkValidCoordinate(coordinate)) {
            throw new IllegalArgumentException(ErrorMessage.COORDINATE_NOT_VALID);
        }
        if(specialPower && !turn.getCurrentPlayer().getGod().hasSpecialStartPower()) {
            throw new IllegalArgumentException(ErrorMessage.NO_SPECIAL_POWER);
        }
        playerGod = turn.getCurrentPlayer().getGod();
        squaresAllowed =
                specialPower ? builder.getBuildableNeighborhood() : builder.getWalkableNeighborhood();
        currentSquare = builder.getPosition();
        actionSquare = game.getBoard().squareAt(coordinate);

        if(!squaresAllowed.contains(actionSquare)) {
            throw new IllegalArgumentException(ErrorMessage.ILLEGAL_MOVE);
        }
        //This point is reached if the action is valid and will be executed
        turn.setActiveBuilder(builder); //Lock the builder for the entire turn
        //Do the proper action
        if(specialPower) {
            builder.build(actionSquare);
            playerGod.setMoveBehavior(new NoUpMove(playerGod.getMoveBehavior())); //Apply no up move decorator if the power is used
            turn.setTurnState(turn.specialMoveState);
            turn.notifyObservers(obs -> {
                obs.receiveBoard(new Board(game.getBoard()));
                obs.receiveAllowedSquares(builder, builder.getWalkableNeighborhood());
            });
        } else {
            canMoveAgain = builder.move(actionSquare);
            if(canMoveAgain) {
                turn.setTurnState(turn.additionalMoveState);
                turn.notifyObservers(obs -> {
                    obs.receiveBuildersPositions(game.getAllBuilders());
                    obs.receiveAllowedSquares(builder, builder.getWalkableNeighborhood());
                });
            } else {
                turn.setTurnState(turn.buildState);
                turn.notifyObservers(obs -> {
                    obs.receiveBuildersPositions(game.getAllBuilders());
                    obs.receiveAllowedSquares(builder, builder.getBuildableNeighborhood());
                });
            }
            playerGod.checkWinCondition(currentSquare, builder).ifPresent(game::setWinner);
        }
        turn.notifyObservers(TurnObserver::receiveUpdateDone);
        if(specialPower) {
            turn.getCurrentPlayer().getGod().checkSpecialWinCondition().ifPresent(game::setWinner);
        }
        return true;
    }

    public boolean selectCoordinate(Coordinate c, boolean specialPower) {
        return false;
    }

    public boolean selectCoordinate(Coordinate c) {
        return false;
    }

    public boolean endPhase() {
        return false;
    }

    public Turn.State getStateID() {
        return Turn.State.MOVE;
    }

}
