package model.turnstates;

import model.Builder;
import model.Game;
import model.Turn;
import model.turnstates.TurnState;
import utils.Coordinate;

public class EndTurnState implements TurnState {
    private final Turn turn;
    private final Game game;

    public EndTurnState(Turn turn, Game game){
        this.turn = turn;
        this.game = game;
    }

    public boolean firstSelection(Builder b, Coordinate c, boolean specialPower) {
        return false;
    }

    public boolean selectCoordinate(Coordinate c, boolean specialPower) {
        return false;
    }

    public boolean selectCoordinate(Coordinate c) {
        return false;
    }

    public boolean endPhase() {
        game.nextTurn(false);
        return true;
    }

    public Turn.State getStateID() {
        return Turn.State.END_TURN;
    }
}
