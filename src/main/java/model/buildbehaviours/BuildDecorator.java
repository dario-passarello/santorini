package model.buildbehaviours;

import model.Square;

import java.util.HashSet;
import java.util.Set;

//TODO
public abstract class BuildDecorator implements BuildBehavior {
    protected BuildBehavior wrappedBuildBehavior;

    public Set<Square> neighborhood(Square src){
        return null;
    }

    public boolean build(Square dest){
        return false;
    }
}
