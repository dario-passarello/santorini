package model.buildbehaviours;

import model.Square;
import model.movebehaviors.MoveBehavior;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DoubleSameBuild extends BuildDecorator {

    // Keeps track of the order of the builds
    private boolean second;
    private Square previous;


    public boolean build(Square dest) {
        if(!second){
            second = true;
            previous = dest;
            wrappedBuildBehavior.build(dest);
            return true;
        }
        else return wrappedBuildBehavior.build(dest);
    }

    /**
     * The first iteration of the methods is exactly like the StandardBuild
     * The second iteration of the method returns only the square where the player had previously built
     * @param src the position of the builder that wants to build
     * @return the set of squares where the builder can build
     */
    public Set<Square> neighborhood(Square src) {
        if(!second){
            return(this.wrappedBuildBehavior.neighborhood(src));
        }
        else{
            HashSet<Square> buildable = new HashSet<>();
            if((previous.getBuildLevel() < 3) || (previous.getBuildLevel() == 3 && !previous.isDomed()))
                buildable.add(previous);
            return buildable;
        }
    }



    public DoubleSameBuild(BuildBehavior buildBehavior){
        this.wrappedBuildBehavior = buildBehavior;
        second = false;
        previous = null;
    }



}
