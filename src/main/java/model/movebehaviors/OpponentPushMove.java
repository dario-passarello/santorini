package model.movebehaviors;

import model.Builder;
import model.Square;

import java.util.List;
import java.util.Set;

//TODO
public class OpponentPushMove extends MoveDecorator {

    public OpponentPushMove(MoveBehavior moveBehavior){
        wrappedMoveBehavior = moveBehavior;
    }

    /**
     * @param src is the starting point of a builder
     * @return the standard neighborhood + special neighborhood
     * (we also consider reachable squares occupied by an opponent builder)
     */
    public Set<Square> neighborhood(Square src) {
        return null;
    }

    /**
     * @param b is the builder we want to move
     * @param dest is the square where our builder want to go
     * @return a boolean that indicates if the move phase is ended or not
     *
     * if b goes to a square occupied by an opponent builder, he will push him in the same direction
     */
    public boolean move(Builder b, Square dest) {

    }

}
