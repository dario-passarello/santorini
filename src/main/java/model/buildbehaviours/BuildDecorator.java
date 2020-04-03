package model.buildbehaviours;

import model.Square;

import java.util.HashSet;

//TODO
public abstract class BuildDecorator implements BuildBehavior {
    protected BuildBehavior wrappedBuildBehavior;

    public HashSet<Square> neighborhood(Square src){
        return null;
    }

    public boolean build(Square dest){
        return false;
    }
}
