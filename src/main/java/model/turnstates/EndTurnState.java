package model.turnstates;

import model.Game;
import model.Turn;
import utils.Coordinate;

/**
 * This class represents the moment where a Turn has ended
 */
public class EndTurnState implements TurnState {
    private final Turn turn;
    private final Game game;

    /**
     * The constructor of the class
     * @param turn A reference to the current turn
     * @param game A reference to the current game
     */
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
