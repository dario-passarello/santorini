package model.turnstates;

import model.*;
import model.gods.God;
import model.movebehaviors.NoUpMove;
import utils.Coordinate;

import java.util.List;

/**
 * This class represents the first action of the Turn. It could either represent a regular move or the use of a
 * starting god power
 */
public class MoveState implements TurnState {
    private final Turn turn;
    private final Game game;

    /**
     * The constructor of the class
     * @param turn A reference to the current turn
     * @param game A reference to the current game
     */
    public MoveState(Turn turn, Game game){
        this.turn = turn;
        this.game = game;
    }

    @Override
    public boolean firstSelection(int builderID, Coordinate coordinate, boolean specialPower) {
        List<Square> squaresAllowed; //Squares where the player could go
        Square currentSquare;   //The square were the active builder is located
        Square actionSquare;    //The square selected from the player
        God playerGod;
        Builder builder;
        boolean canMoveAgain;
        if(0 > builderID || builderID > Player.BUILDERS_PER_PLAYER)
            throw new IllegalArgumentException();
        builder = turn.getCurrentPlayer().getBuilders().get(builderID);
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
        currentSquare = builder.getSquare();
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
                obs.receiveAllowedSquares(builder, builder.getWalkableCoordinates(), false);
                obs.receiveUpdateDone();
            });
        } else {
            canMoveAgain = builder.move(actionSquare);
            if(canMoveAgain) {
                turn.setTurnState(turn.additionalMoveState);
                turn.notifyObservers(obs -> {
                    obs.receiveBuildersPositions(game.getAllBuilders());
                    obs.receiveAllowedSquares(builder, builder.getWalkableCoordinates(), false);
                    obs.receiveUpdateDone();
                });
            } else {
                if(builder.getBuildableCoordinates().isEmpty())
                    game.removePlayer(turn.getCurrentPlayer(), false); //Check special Apollo edge case (Switch to another stuck builder)
                turn.setTurnState(turn.buildState);
                turn.notifyObservers(obs -> {
                    obs.receiveBuildersPositions(game.getAllBuilders());
                    obs.receiveAllowedSquares(builder, builder.getBuildableCoordinates(), false);
                    if(playerGod.hasSpecialBuildPower()) {
                        obs.receiveAllowedSquares(builder, builder.getBuildableCoordinates(), true);
                    }
                    obs.receiveUpdateDone();
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

    @Override
    public boolean selectCoordinate(Coordinate coordinate, boolean specialPower) {
        return false;
    }

    @Override
    public boolean endPhase() {
        return false;
    }

    @Override
    public Turn.State getStateID() {
        return Turn.State.MOVE;
    }

}
