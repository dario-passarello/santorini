package model.turnstates;

import model.Game;
import model.Turn;
import utils.Coordinate;

@Deprecated
public class EndTurnState implements TurnState {
    private final Turn turn;
    private final Game game;

    public EndTurnState(Turn turn, Game game){
        this.turn = turn;
        this.game = game;
    }

    @Override
    public boolean firstSelection(int builderID, Coordinate coordinate, boolean specialPower) {
        return false;
    }

    @Override
    public boolean selectCoordinate(Coordinate coordinate, boolean specialPower) {
        return false;
    }

    @Override
    public boolean endPhase() {
        turn.getCurrentPlayer().getGod().resetBehaviors();
        game.nextTurn(false);
        return true;
    }

    @Override
    public Turn.State getStateID() {
        return Turn.State.END_TURN;
    }
}
