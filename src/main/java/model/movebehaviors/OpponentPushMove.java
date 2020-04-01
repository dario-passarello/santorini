package model.movebehaviors;

import model.Builder;
import model.Square;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//TODO
public class OpponentPushMove extends MoveDecorator {

    public OpponentPushMove(MoveBehavior moveBehavior){
        wrappedMoveBehavior = moveBehavior;
    }

    /**
     * @param src is the starting point of a builder
     * @return the standard neighborhood + special neighborhood
     * (we also consider reachable squares occupied by an opponent builder)
     */
    public HashSet<Square> neighborhood(Square src) {
        HashSet<Square> adjacent = src.getNeighbors();
        HashSet<Square> neighborhood = new HashSet<Square>();
        for(Square square : adjacent){
            if( (square.getBuildLevel() - src.getBuildLevel()) <= 1 &&
                    square.getBuilder() != null &&
                    (square.getBuilder().getOwner() != src.getBuilder().getOwner)){
                neighborhood.add(square);
            }
        }

        return wrappedMoveBehavior.neighborhood().addAll(neighborhood);
    }


    /**
     * @param b is the builder we want to move
     * @param dest is the square where our builder want to go
     * @return a boolean that indicates if the move phase is ended or not
     *
     * if b goes to a square occupied by an opponent builder, he will push him in the same direction
     */
    public boolean move(Builder b, Square dest) {
        return false;
    }

}
