package model.turnstates;

import model.*;
import utils.Coordinate;

public class ShowBuildableSpecialState implements TurnState {
    private final Turn turn;
    private final Game game;
    private final boolean dome;

    public ShowBuildableSpecialState(Turn turn, Game game, Boolean dome){
        this.turn = turn;
        this.game = game;
        this.dome = dome;
    }

    public void onEntry() {

    }

    public void onExit() {

    }

    public boolean selectBuilder(Builder b) {
        return false;
    }

    public boolean useGodPower() {
        turn.setTurnState(turn.showBuildableFirstState);
        //TODO Notify Observer - State Changed
        return true;
    }

    public boolean selectCoordinate(Coordinate c) throws IllegalArgumentException {
        Square destSquare;
        boolean canBuildAgain;
        if(Board.checkValidCoordinate(c))
            throw new IllegalArgumentException("Coordinate out of range");
        destSquare = game.getBoard().squareAt(c);
        if(!turn.getActiveBuilder().getBuildableNeighborhood().contains(destSquare)) {
            throw new IllegalArgumentException("Coordinate not buildable from the active builder position");
        }
        if(!dome) {
            turn.getActiveBuilder().build(destSquare);
            canBuildAgain = turn.getActiveBuilder().build(destSquare);
            if(canBuildAgain){
                turn.setTurnState(turn.showBuildableAdditionalState);
            } else {
                turn.setTurnState(turn.endTurnState);
            }
        } else {
            turn.getActiveBuilder().buildDome(destSquare);
            turn.setTurnState(turn.endTurnState);
        }
        //TODO Notify Observer - Build, State Changed
        return true;
    }

    public boolean endPhase() {
        return false;
    }
}
