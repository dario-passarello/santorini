package model.buildbehaviours;

import model.Square;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BeneathBuild extends BuildDecorator{

    public BeneathBuild(BuildBehavior buildBehavior){
        this.wrappedBuildBehavior = buildBehavior;
    }

    public boolean build(Square dest) {
        return wrappedBuildBehavior.build(dest);
    }

    /**
     * It is like the StandardBuild, but adds the square the builder is currently positioned on
     * @param src the position of the builder that wants to build
     * @return the list of squares the builder can build
     */
    public Set<Square> neighborhood(Square src) {
        Set<Square> buildable = wrappedBuildBehavior.neighborhood(src);
        if(src.getBuildLevel() <= 2) buildable.add(src);
        return buildable;

    }

    public BuildBehavior copyBehavior(){
        return new BeneathBuild(wrappedBuildBehavior.copyBehavior());
    }

}
