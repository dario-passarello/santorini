package model.movebehaviors;

import model.Builder;
import model.Square;

import java.util.HashSet;
import java.util.Set;



public class StandardMove implements MoveBehavior {

    public StandardMove(){}


    /**
     * @param src is the starting point of a builder
     * @return the set of square of a standard neighborhood
     * (squares adjacent to src, not occupied and a level less or equal than level of src + 1
     */
    public Set<Square> neighborhood(Square src) {
        Set<Square> adjacent = src.getNeighbors();
        Set<Square> neighborhood = new HashSet<>();
        for (Square square: adjacent){
            if( (square.getBuildLevel() - src.getBuildLevel()) <= 1 &&
                    !square.isDomed() &&
                    !square.getOccupant().isPresent()){
                neighborhood.add(square);
            }
        }
        return neighborhood;
    }

    /**
     * @param b is the builder we want to move
     * @param dest is the square where our builder want to go
     * @return a boolean that indicates if the move phase is ended or not
     *
     * we need to:
     * - remove the builder reference from the starting square
     * - add his reference to the ending square
     * - set his position to the ending square
     *
     */
    public boolean move(Builder b, Square dest) {

        Square start = b.getSquare();
        dest.setOccupant(b);
        start.setEmptySquare();
        b.setSquare(dest);

        return false;
    }

    @Override
    public void reset() {

    }

}
