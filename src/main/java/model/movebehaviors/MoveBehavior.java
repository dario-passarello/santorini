package model.movebehaviors;

import model.Builder;
import model.Square;
import model.buildbehaviours.StandardBuild;

import java.util.Set;

/**
 *  Contains the strategies for finding the reachable squares and for moving the builder.
 *  Created using the decorator pattern. {@link StandardMove} is the standard move behavior,
 *  custom move behaviors could decorate the Move Behavior
 */
public interface MoveBehavior {

    /**
     * @param src is the starting point of a builder
     * @return the set of squares where he can go
     */
    Set<Square> neighborhood(Square src);

    /**
     * @param b is the builder we want to move
     * @param dest is the square where our builder want to go
     * @return a boolean that indicates if the move phase is ended or not
     *         (true = continue move phase, false = end move phase)
     */
    boolean move(Builder b, Square dest);

    /**
     *  Clones the entire wrapped behavior chain
     */
    MoveBehavior copyBehavior();

    /**
     *  Resets all behavior chain internal states
     */
    void reset();
}
