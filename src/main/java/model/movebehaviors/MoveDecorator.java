package model.movebehaviors;

import model.Builder;
import model.Square;

import java.util.HashSet;
import java.util.Set;

//TODO
public abstract class MoveDecorator implements MoveBehavior {

    public HashSet<Square> neighborhood(Square src){
        return null;
    }

    public boolean move(Builder b, Square dest){

    }
}

