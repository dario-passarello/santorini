package model.movebehaviors;

import model.Builder;
import model.Square;

import java.util.List;
import java.util.Set;

//TODO
public class DoubleNoBackMove extends MoveDecorator {

    public DoubleNoBackMove(MoveBehavior moveBehavior){
        wrappedMoveBehavior = moveBehavior;
    }

    /**
     * @param src is the starting point of a builder
     * @return the special neighborhood
     * (if this is the first move, we will have just a standard one,
     * if this is the second move we cannot go in the starting square of the first move)
     */
    public Set<Square> neighborhood(Square src) {
        return null;
    }

    /**
     * @param b is the builder we want to move
     * @param dest is the square where our builder want to go
     * @return true in the first move, false in the second one
     */
    public boolean move(Builder b, Square dest) {

    }
}
