package model.movebehaviors;

import model.Builder;
import model.Square;

import java.util.HashSet;
import java.util.Set;

//TODO
public class NoUpMove extends MoveDecorator{

    public NoUpMove(MoveBehavior moveBehavior){
        wrappedMoveBehavior = moveBehavior;
    }

    /**
     * @param src is the starting point of a builder
     * @return the standard neighborhood - special neighborhood
     * (the builder cannot go on squares with a level less than the level of src)
     */
    public Set<Square> neighborhood(Square src) {

        Set<Square> adjacent = src.getNeighbors();
        Set<Square> remove = new HashSet<>();
        for(Square square : adjacent){
            if(square.getBuildLevel() > src.getBuildLevel()){
                remove.add(square);
            }
        }
        neighborhood(src).removeAll(remove);
        return wrappedMoveBehavior.neighborhood(src);
    }

    /**
     * @param b is the builder we want to move
     * @param dest is the square where our builder want to go
     * @return a boolean that indicates if the move phase is ended or not
     */
    public boolean move(Builder b, Square dest) {
        return wrappedMoveBehavior.move(b, dest);
    }
}
