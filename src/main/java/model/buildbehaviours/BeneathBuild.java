package model.buildbehaviours;

import model.Square;

import java.util.List;
import java.util.Set;

public class BeneathBuild implements BuildBehavior{


    public boolean build(Square dest) {
        return false;
    }


    /**
     * It is like the StandardBuild, but adds the square the builder is currently positioned on
     * @param src the position of the builder that wants to build
     * @return the list of squares the builder can build
     */
    public Set<Square> neighborhood(Square src) {
        return null;
    }


}
