package model.movebehaviors;

import model.Builder;
import model.Square;

import java.util.HashSet;
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
    public Set<Square> neighborhood(Square src) {

        Set<Square> remove = new HashSet<>();
        remove.add(previous);
        if (previous != null) {                                                    // second move
            neighborhood(src).removeAll(remove);
        }
        return wrappedMoveBehavior.neighborhood(src);                              // first move
    }

    /**
     * @param b is the builder we want to move
     * @param dest is the square where our builder want to go
     * @return true in the first move, false in the second one
     */
    public boolean move(Builder b, Square dest) {
        Square save = b.getPosition();
        wrappedMoveBehavior.move(b, dest);
        if(previous == null){
            previous = save;
            return true;
        } else {
            previous = null;
            return false;
        }
    }
}

