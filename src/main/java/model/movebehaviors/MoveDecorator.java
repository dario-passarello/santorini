package model.movebehaviors;

import model.Builder;
import model.Square;

import java.util.Set;


/**
 *  It's the abstract Move Behavior decorator (all the concrete move decorator must extend this class)
 */
public abstract class MoveDecorator implements MoveBehavior {

    protected MoveBehavior wrappedMoveBehavior;

    public abstract Set<Square> neighborhood(Square src);

    public abstract boolean move(Builder b, Square dest);

    public void reset(){
        wrappedMoveBehavior.reset();
    }
}

