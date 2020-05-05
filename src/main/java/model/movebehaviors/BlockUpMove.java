package model.movebehaviors;

import model.Builder;
import model.Square;

import java.util.Set;

public class BlockUpMove extends MoveDecorator{

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
}
