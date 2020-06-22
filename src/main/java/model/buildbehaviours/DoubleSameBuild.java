package model.buildbehaviours;

import model.Square;
import model.movebehaviors.MoveBehavior;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DoubleSameBuild extends BuildDecorator {

    // Keeps track of the order of the builds
    private Square previous;

    public DoubleSameBuild(BuildBehavior buildBehavior){
        this.wrappedBuildBehavior = buildBehavior;
        previous = null;
    }

    public boolean build(Square dest) {
        if(previous == null){
            previous = dest;
            wrappedBuildBehavior.build(dest);
            return true;
        } else {
            previous = null;
            return wrappedBuildBehavior.build(dest);
        }
    }

    public Set<Square> neighborhood(Square src) {
        if(previous == null){
            return(this.wrappedBuildBehavior.neighborhood(src));
        }
        else{
            HashSet<Square> buildable = new HashSet<>();
            if(previous.getBuildLevel() < 3)
                buildable.add(previous);
            return buildable;
        }
    }

    @Override
    public BuildBehavior copyBehavior(){
        return new DoubleSameBuild(wrappedBuildBehavior.copyBehavior());
    }

    @Override
    public void reset() {
        previous = null;
        wrappedBuildBehavior.reset();
    }
}
