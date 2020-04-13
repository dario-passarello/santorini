package model.turnstates;

import model.Builder;
import model.Game;
import model.Square;
import model.Turn;
import utils.Coordinate;

import java.util.List;

public class BuilderSelectionState implements TurnState {
    private final  Turn turn;
    private final Game game;
    private final boolean special;

    public BuilderSelectionState(Turn turn, Game game, Boolean special){
        this.turn = turn;
        this.game = game;
        this.special = special;
    }

    public void onEntry() {

        if(turn.getCurrentPlayer().checkMovingLoseCondition()){
            //The player lost, switch turn and remove from the turn cycle
            //TODO Notify Observers  - Lost
            game.nextTurn();
        }
    }

    public void onExit() {

    }

    public boolean selectBuilder(Builder b) throws IllegalArgumentException {
        List<Square> squareListToShow;
        if(!b.getOwner().equals(turn.getCurrentPlayer()))
            throw new IllegalArgumentException("Selected builder Must Be Controlled by the current player!");
        turn.setActiveBuilder(b);
        if(special) {
            squareListToShow = turn.getActiveBuilder().getBuildableNeighborhood();
            turn.setTurnState(turn.showBuildableSpecialStartState);
            //TODO Notify observers - Show buildable
        }
        else {
            squareListToShow = turn.getActiveBuilder().getWalkableNeighborhood();
            turn.setTurnState(turn.showWalkableDeselectableState);
            //TODO Notify observers - Show walkable
        }
        return true;
    }

    public boolean useGodPower() {
        if(!turn.getCurrentPlayer().getGod().hasSpecialStartPower())
            return false; //The player has no special start phase
        if(special)
            turn.setTurnState(turn.builderSelectionState);
        else
            turn.setTurnState(turn.builderSelectionSpecialState);
        //TODO Notify Observers
        return true;
    }

    public boolean selectCoordinate(Coordinate c) {
        return false;
    }

    public boolean endPhase() {
        return false;
    }
}
