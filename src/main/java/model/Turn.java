package model;

import model.gamestates.TurnState;
import utils.Coordinate;

public class Turn {
    Game game;
    TurnState currentState;
    Player currentPlayer;
    public Turn(Player currentPlayer) {

    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public TurnState getTurnState() {
        return null; //TODO
    }

    public void setTurnState(TurnState turn) {

    }


    public void selectBuilder(Builder b) {
        //TODO
    }

    public void useGodPower() {
        //TODO
    }

    public void selectCoordinate(Coordinate c) {
        //TODO
    }

    public void endPhase() {
        //TODO
    }

}
