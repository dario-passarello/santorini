package model.movebehaviors;

import model.Builder;
import model.Square;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//TODO
public class NoUpMove extends MoveDecorator{

    public NoUpMove(MoveBehavior moveBehavior){
        wrappedMoveBehavior = moveBehavior;
    }

    /**
     * @param src is the starting point of a builder
     * @return the standard neighborhood - special neighborhood
     * (the builder cannot go on squares with a level < level of src)
     */
    public HashSet<Square> neighborhood(Square src) {

        HashSet<Square> adjacent = src.getNeighbors();
        HashSet<Square> remove = new HashSet<>();
        for(Square square : adjacent){
            if(square.getBuildLevel() > src.getBuildLevel()){
                remove.add(square);
            }
        }

        return wrappedMoveBehavior.neighborhood(src).removeAll(remove);
    }

    /**
     * @param b is the builder we want to move
     * @param dest is the square where our builder want to go
     * @return a boolean that indicates if the move phase is ended or not
     */
    public boolean move(Builder b, Square dest) {
        return false;
    }
}
