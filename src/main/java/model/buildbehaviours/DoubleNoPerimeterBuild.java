package model.buildbehaviours;

import model.Square;

import java.util.List;
import java.util.Set;

public class DoubleNoPerimeterBuild implements  BuildBehavior{

    // Keeps track of the order of the builds
    private boolean second;


    public boolean build(Square dest) {
        return false;
    }

    /**
     * the first iteration of the method is exactly like the StandardBuild
     * the second iteration of the method returns only non-perimetral squares
     * @param src the position of the builder that wants to build
     * @return
     */
    public Set<Square> neighborhood(Square src) {
        return null;
    }


}
