package model.movebehaviors;

import model.Square;

import java.util.List;


//TODO
public class StandardMove implements MoveBehavior {

    private Integer numMoves;

    public Integer getNumMoves() {
        return numMoves;
    }

    public StandardMove(){}


    public List<Square> neighborhood(Square src) {
        return null;
    }

    public void move(Square dest) {
    }

    public boolean endMove() {
        return false;
    }
}
