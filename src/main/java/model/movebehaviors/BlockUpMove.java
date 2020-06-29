package model.movebehaviors;

import model.Builder;
import model.Square;

import java.util.Set;

/**
 * It's a Move Behavior decorator that adds the following effect:
 * if the builder move up, opponent builders would not be able to move up
 * on their next turn (their move behaviors would be decorated with {@link NoUpMove}
 */
public class BlockUpMove extends MoveDecorator{

    /**
     * The constructor method. It decorates the parameter with this class
     * @param moveBehavior The Move Behavior target
     */
    public BlockUpMove(MoveBehavior moveBehavior){
        wrappedMoveBehavior = moveBehavior;
    }

    public Set<Square> neighborhood(Square src) {
        return wrappedMoveBehavior.neighborhood(src);
    }

    /**
     * @param b is the builder we want to move
     * @param dest is the square where our builder want to go
     * @return a boolean that indicates if the move phase is ended or not
     */
    public boolean move(Builder b, Square dest) {
        if(dest.getBuildLevel() > b.getSquare().getBuildLevel()){                //if you move up, other builder will not move up on their next turn
            b.getOwner().getGod().setAllMoveBehaviors(b.getOwner().getGame().getGodList());
        }
        return wrappedMoveBehavior.move(b, dest);
    }

    @Override
    public MoveBehavior copyBehavior() {
        return new BlockUpMove(wrappedMoveBehavior.copyBehavior());
    }
}
