package model.movebehaviors;

import model.Builder;
import model.Square;

import java.util.Set;

/**
 * It's a Move Behavior decorator that, each time your builder moves into a perimeter square,
 * adds the possibility to immediately move again
 */
public class UnlimitedPerimetralMove extends MoveDecorator {

    /**
     * The constructor method. It decorates the parameter with this class
     * @param moveBehavior The Move Behavior target
     */
    public UnlimitedPerimetralMove(MoveBehavior moveBehavior){
        wrappedMoveBehavior = moveBehavior;
    }

    public Set<Square> neighborhood(Square src) {
        return wrappedMoveBehavior.neighborhood(src);
    }

    /**
     * @param b is the builder we want to move
     * @param dest is the square where our builder want to go
     * @return a boolean that indicates if the move phase is ended or not
     *
     * it will return true every time dest is in the perimeter
     */
    public boolean move(Builder b, Square dest) {
        wrappedMoveBehavior.move(b, dest);
        return b.getSquare().isPerimetral();
    }

    @Override
    public MoveBehavior copyBehavior() {
        return new UnlimitedPerimetralMove(wrappedMoveBehavior.copyBehavior());
    }
}
