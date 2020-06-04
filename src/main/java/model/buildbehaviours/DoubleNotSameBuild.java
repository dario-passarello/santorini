package model.buildbehaviours;

import model.Square;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DoubleNotSameBuild extends BuildDecorator {

    // Contains the Square the player has built on the first time
    // Null - if this is the first ordinary build of the turn
    private Square previous;


    public DoubleNotSameBuild(BuildBehavior buildBehavior){
        this.wrappedBuildBehavior = buildBehavior;
        this.previous = null;
    }

    public boolean build(Square dest) {
        if(previous == null){
            previous = dest;
            wrappedBuildBehavior.build(dest);
            return true;
        }
        else {
            previous = null;
            return wrappedBuildBehavior.build(dest);
        }
    }

    /**
     * The first iteration of the method is exactly like the standard build
     * The second iteration of the methods makes sure the previously built square is not present
     * @param src the position of the builder that wants to build
     * @return the set of square where the builder can build
     */
    public Set<Square> neighborhood(Square src) {
        if(previous == null){
            return this.wrappedBuildBehavior.neighborhood(src);
        }
        else{
            Set<Square> buildable = this.wrappedBuildBehavior.neighborhood(src);
            if(buildable.contains(previous)) buildable.remove(previous);
            return buildable;
        }
    }

    @Override
    public void reset() {
        previous = null;
        wrappedBuildBehavior.reset();
    }
}
