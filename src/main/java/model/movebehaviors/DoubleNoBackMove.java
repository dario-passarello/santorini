package model.movebehaviors;

import model.Builder;
import model.Square;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//TODO
public class DoubleNoBackMove extends MoveDecorator {

    Square previous;

    public DoubleNoBackMove(MoveBehavior moveBehavior){
        wrappedMoveBehavior = moveBehavior;
        previous = null;
    }

    /**
     * @param src is the starting point of a builder
     * @return the special neighborhood
     * (if this is the first move, we will have just a standard one,
     * if this is the second move we cannot go in the starting square of the first move)
     */
    public HashSet<Square> neighborhood(Square src) {

        HashSet<Square> remove = new HashSet<>();
        remove.add(previous);
        if(previous == null){
            return wrappedMoveBehavior.neighborhood()                               // first move
        }
        else{
            return wrappedMoveBehavior.neighborhood().removeAll(remove);            // second move
        }

        return null;
    }

    /**
     * @param b is the builder we want to move
     * @param dest is the square where our builder want to go
     * @return true in the first move, false in the second one
     */
    public boolean move(Builder b, Square dest) {
        return false;
    }
}
