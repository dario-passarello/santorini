package model.buildbehaviours;

import model.Square;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLongArray;

/**
 * It's the abstract Build Behavior decorator (all the concrete build decorator must extend this class)
 */
public abstract class BuildDecorator implements BuildBehavior {

    /**
     * The wrapped Build Behavior
     */
    protected BuildBehavior wrappedBuildBehavior;

    public abstract Set<Square> neighborhood(Square src);

    public abstract boolean build(Square dest);

    public void reset(){
        wrappedBuildBehavior.reset();
    }
}
