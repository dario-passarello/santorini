package model.buildbehaviours;

import model.Square;

import java.util.List;
import java.util.Set;

public class DoubleNotSameBuild implements BuildBehavior {

    // Contains the Square the player has built on the first time
    // Null - if this is the first ordinary build of the turn
    private Square previous;


    public boolean build(Square dest) {
        return false;
    }

    /**
     * The first iteration of the method is exactly like the standard build
     * The second iteration of the methods makes sure the previously built square is not present
     * @param src the position of the builder that wants to build
     * @return the set of square where the builder can build
     */
    public Set<Square> neighborhood(Square src) {
        return null;
    }


}
