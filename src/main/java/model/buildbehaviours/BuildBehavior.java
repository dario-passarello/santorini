package model.buildbehaviours;

import model.Square;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface BuildBehavior {

    /**
     * @param dest is the square the player wants to build on
     * @return true if the player can build an additional time
     */
    boolean build(Square dest);


    /**
     * @param src the position of the builder that wants to build
     * @return the set of squares where the builder can build
     */
    HashSet<Square> neighborhood(Square src);


}
