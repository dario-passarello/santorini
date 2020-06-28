package model.buildbehaviours;

import model.Square;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * It's a Build Behavior decorator that adds the possibility to build twice (the second build can't be on the same square)
 */
public class DoubleNotSameBuild extends BuildDecorator {

    // Contains the Square the player has built on the first time
    // Null - if this is the first ordinary build of the turn
    private Square previous;

    /**
     * The constructor method. It decorates the parameter with this class
     * @param buildBehavior The Build Behavior target
     */
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
    public BuildBehavior copyBehavior(){
        return new DoubleNotSameBuild(wrappedBuildBehavior.copyBehavior());
    }

    @Override
    public void reset() {
        previous = null;
        wrappedBuildBehavior.reset();
    }
}
