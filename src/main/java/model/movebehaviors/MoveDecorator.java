package model.movebehaviors;

import model.Builder;
import model.Square;

import java.util.Set;

//TODO
public abstract class MoveDecorator implements MoveBehavior {
    protected MoveBehavior wrappedMoveBehavior;

    public Set<Square> neighborhood(Square src){
        return null;
    }

    public boolean move(Builder b, Square dest){

    }
}

