package model.turnstates;

import model.*;
import utils.Coordinate;

import java.util.List;

public class ShowWalkableState implements TurnState {

    private final Turn turn;
    private final Game game;
    private final boolean deselectable;
    private final boolean firstMove;

    public ShowWalkableState(Turn turn, Game game, boolean deselectable, boolean firstMove){
        this.turn = turn;
        this.game = game;
        this.deselectable = deselectable;
        this.firstMove = firstMove;
    }

    @Override
    public void onEntry() {

    }

    @Override
    public void onExit() {

    }

    public boolean selectBuilder(Builder b) throws IllegalArgumentException {
        List<Square> squareListToShow;
        if(!deselectable || !firstMove) return false; //Could not change selected builder
        if(!b.getOwner().equals(turn.getCurrentPlayer()))
            throw new IllegalArgumentException("Selected builder Must Be Controlled by the current player!");
        if(b.equals(turn.getActiveBuilder())) {
            turn.setTurnState(turn.builderSelectionState);
            List<Builder> builders = turn.getCurrentPlayer().getBuilders();
            //TODO Notify Observer - Deselected, show builders
        }
        else {
            turn.setActiveBuilder(b);
            squareListToShow = turn.getActiveBuilder().getWalkableNeighborhood();
            turn.setTurnState(turn.showWalkableDeselectableState);
            //TODO Notify Observer - Show Walkable
        }
        return true;
    }

    @Override
    public boolean useGodPower() {
        return false;
    }

    @Override
    public boolean selectCoordinate(Coordinate c) throws IllegalArgumentException {
        Square destSquare;
        boolean canMoveAgain;
        if(!Board.checkValidCoordinate(c)){
            throw new IllegalArgumentException("Coordinate out of range");
        }
        destSquare = game.getBoard().squareAt(c);
        if(!turn.getActiveBuilder().getWalkableNeighborhood().contains(destSquare)){
            throw new IllegalArgumentException("Coordinate not walkable from the active builder position");
        }
        canMoveAgain = turn.getActiveBuilder().move(destSquare);
        if(canMoveAgain) {
            turn.setTurnState(turn.showWalkableAdditionalState);
        } else {
            turn.setTurnState(turn.showBuildableFirstState);
        }
        //TODO: Notify Observers - Moved, State Change
        return true;
    }

    @Override
    public boolean endPhase() {
        if(firstMove) return false; //You can't skip first move
        turn.setTurnState(turn.showBuildableFirstState);
        //TODO: Notify Observers - State Change
        return true;
    }
}
