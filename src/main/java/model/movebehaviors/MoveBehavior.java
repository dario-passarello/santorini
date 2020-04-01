package model.movebehaviors;

import model.Builder;
import model.Square;

import java.util.HashSet;
import java.util.List;

//TODO
public interface MoveBehavior {

    /**
     * @param src is the starting point of a builder
     * @return the set of squares where he can go
     */
    HashSet<Square> neighborhood(Square src);


    /**
     * @param b is the builder we want to move
     * @param dest is the square where our builder want to go
     * @return a boolean that indicates if the move phase is ended or not
     *         (true = continue move phase, false = end move phase)
     */
    boolean move(Builder b, Square dest);
}
