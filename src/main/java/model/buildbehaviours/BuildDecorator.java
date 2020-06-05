package model.buildbehaviours;

import model.Square;

import java.util.HashSet;
import java.util.Set;

//TODO
public abstract class BuildDecorator implements BuildBehavior {
    protected BuildBehavior wrappedBuildBehavior;

    public abstract Set<Square> neighborhood(Square src);

    public abstract boolean build(Square dest);

    public void reset(){
        wrappedBuildBehavior.reset();
    }
}
