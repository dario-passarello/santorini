package model.turnstates;

import model.*;
import utils.Coordinate;

import java.util.List;

/**
 * This class represents the building Phase. In this state the player can build a block or a dome
 * An instance of this class could represent either an optional phase or a compulsory one
 */
public class BuildState implements TurnState {

    private final Turn turn;
    private final Game game;
    private final boolean optional;

    /**
     * The constructor of the class
     * @param turn A reference to the current turn
     * @param game A reference to the current game
     * @param optional A parameter that specifies if the state is optional or not
     */
    public BuildState(Turn turn, Game game, boolean optional){
        this.turn = turn;
        this.game = game;
        this.optional = optional;
    }

    @Override
    public boolean firstSelection(int builderID, Coordinate coordinate, boolean specialPower) {
        return false;
    }

    /**
     * This method is called when a building action is executed. It builds a block or a dome on the selected square
     * and manages the future transition based on the current setup of the game
     * @param coordinate The coordinate target
     * @param specialPower The parameter which represent if the action is a special one or not
     * @return True if the method is called from a legit state. False otherwise
     */
    @Override
    public boolean selectCoordinate(Coordinate coordinate, boolean specialPower) {
        List<Square> buildableSquares;
        Builder activeBuilder = turn.getActiveBuilder();
        Square buildSquare;
        boolean canBuildAgain = false;
        boolean endTurn = false;
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
            endTurn = true;
        } else {
            canBuildAgain = activeBuilder.build(buildSquare);
            if(canBuildAgain) {
                turn.setTurnState(turn.additionalBuildState);
                //Send the buildable square for the additional build phase
                turn.notifyObservers(obs -> obs.receiveAllowedSquares(activeBuilder,activeBuilder.getBuildableCoordinates(), false));
            } else {
                endTurn = true;
            }
        }
        //Check the special winning condition (after the build)
        turn.getCurrentPlayer().getGod().checkSpecialWinCondition().ifPresent(game::setWinner);
        if(endTurn){
            turn.endTurn();
        } else {
            turn.notifyObservers(obs -> {
                obs.receiveBoard(new Board(game.getBoard()));
                obs.receiveUpdateDone();
            });
        }
        return true;
    }

    @Override
    public boolean endPhase() {
        if(!optional)
            throw new IllegalStateException(ErrorMessage.NOT_OPTIONAL_STATE); //The state is not optional, could not be skipped
        turn.endTurn();
        return true;
    }

    @Override
    public Turn.State getStateID() {
        return optional ? Turn.State.ADDITIONAL_BUILD : Turn.State.BUILD;
    }
}
