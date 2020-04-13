package model.turnstates;

import model.Builder;
import model.Game;
import model.Turn;
import utils.Coordinate;

public class SelectionState implements TurnState {
    private Turn turn;
    private Game game;

    public SelectionState(Turn turn, Game game){
        this.turn = turn;
        this.game = game;
    }

    public void onEntry() {

    }

    public void onExit() {

    }

    public boolean selectBuilder(Builder b) {
        return false;
    }

    public boolean useGodPower() {
        return false;
    }

    public boolean selectCoordinate(Coordinate c) {
        return false;
    }

    public boolean endPhase() {
        return false;
    }
}
