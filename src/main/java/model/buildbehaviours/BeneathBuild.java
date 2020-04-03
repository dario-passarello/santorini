package model.buildbehaviours;

import model.Square;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BeneathBuild extends BuildDecorator{

    private BuildBehavior wrappedBuildBehavior;

    public boolean build(Square dest) {
        return false;
    }


    /**
     * It is like the StandardBuild, but adds the square the builder is currently positioned on
     * @param src the position of the builder that wants to build
     * @return the list of squares the builder can build
     */
    public HashSet<Square> neighborhood(Square src) {
        HashSet<Square> buildable = wrappedBuildBehavior.neighborhood(src);
        buildable.add(src);
        return buildable;
    }

    public BeneathBuild(BuildBehavior buildBehavior){
        this.wrappedBuildBehavior = buildBehavior;
    }

}
