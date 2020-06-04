package model.movebehaviors;

import model.Builder;
import model.Square;

import java.util.Set;

//TODO
public abstract class MoveDecorator implements MoveBehavior {

    protected MoveBehavior wrappedMoveBehavior;

    public abstract Set<Square> neighborhood(Square src);

    public abstract boolean move(Builder b, Square dest);

    public void reset(){
        wrappedMoveBehavior.reset();
    }
}

