package model.turnstates;

import model.*;
import utils.Coordinate;

public class ShowBuildableState implements TurnState {
    private final Turn turn;
    private final Game game;
    private final boolean firstBuild;

    public ShowBuildableState(Turn turn, Game game, boolean firstBuild){
        this.turn = turn;
        this.game = game;
        this.firstBuild = firstBuild;
    }


    public void onEntry() {

    }


    public void onExit() {

    }


    public boolean selectBuilder(Builder b) {
        return false;
    }


    public boolean useGodPower() {
        if(!turn.getCurrentPlayer().getGod().hasSpecialBuildPower() || !firstBuild)
            return false;
        turn.setTurnState(turn.showBuildableSpecialDomeState);
        //TODO Notify Observers - State Changed
        return true;
    }

    public boolean selectCoordinate(Coordinate c) {
        Square destSquare;
        boolean canBuildAgain;
        if(!Board.checkValidCoordinate(c)){
            throw new IllegalArgumentException("Coordinate out of range");
        }
        destSquare = game.getBoard().squareAt(c);
        if(!turn.getActiveBuilder().getBuildableNeighborhood().contains(destSquare)){
            throw new IllegalArgumentException("Coordinate not buildable from the active builder position");
        }
        canBuildAgain = turn.getActiveBuilder().move(destSquare);
        if(canBuildAgain){
            turn.setTurnState(turn.showBuildableAdditionalState);
        } else {
            turn.setTurnState(turn.endTurnState);
        }
        //TODO Notify Observers - Build, state changed
        return true;
    }

    public boolean endPhase() {
        if(firstBuild) return false;
        turn.setTurnState(turn.endTurnState);
        //TODO Notify Observers - State Changed
        return true;
    }
}
