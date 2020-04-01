package model.movebehaviors;

import model.Builder;
import model.Square;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UnlimitedPerimetralMove extends MoveDecorator {

    public UnlimitedPerimetralMove(MoveBehavior moveBehavior){
        wrappedMoveBehavior = moveBehavior;
    }

    public HashSet<Square> neighborhood(Square src) {
        return wrappedMoveBehavior.neighborhood();
    }

    /**
     * @param b is the builder we want to move
     * @param dest is the square where our builder want to go
     * @return a boolean that indicates if the move phase is ended or not
     *
     * it will return true every time dest is in the perimeter
     */
    public boolean move(Builder b, Square dest) {
    return false;
    }
}
