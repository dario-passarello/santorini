package model.buildbehaviours;

import model.Square;

import java.util.List;
import java.util.Set;

public class DoubleSameBuild implements BuildBehavior {

    // Keeps track of the order of the builds
    private boolean second;


    public boolean build(Square dest) {
        return false;
    }

    /**
     * The first iteration of the methods is exactly like the StandardBuild
     * The second iteration of the method returns only the square where the player had previously built
     * @param src the position of the builder that wants to build
     * @return the set of squares where the builder can build
     */
    public Set<Square> neighborhood(Square src) {
        return null;
    }



}
