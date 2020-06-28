package model.buildbehaviours;

import model.Square;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  Contains the strategies for finding the buildable squares and for moving the builder.
 *  Created using the decorator pattern. {@link StandardBuild} is the standard build behavior,
 *  custom build behaviors could decorate the Build Behavior
 */
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
    Set<Square> neighborhood(Square src);

    /**
     *  Clones the entire wrapped behavior chain
     * @return A copy of @this
     */
    BuildBehavior copyBehavior();

    /**
     *  Resets all behavior chain internal states
     */
    void reset();

}
