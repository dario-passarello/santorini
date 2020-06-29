package model.movebehaviors;

import model.Builder;
import model.Square;

import java.util.HashSet;
import java.util.Set;


/**
 * It's a Move Behavior decorator that adds the possibility to move into an opponent builder’s
 * square by forcing their builder to the space yours just vacated.
 */
public class SwapWithOpponentMove extends MoveDecorator {

    /**
     * The constructor method. It decorates the parameter with this class
     * @param moveBehavior The Move Behavior target
     */
    public SwapWithOpponentMove(MoveBehavior moveBehavior){
        wrappedMoveBehavior = moveBehavior;
    }

    /**
     * @param src is the starting point of a builder
     * @return the standard neighborhood + special neighborhood
     * (we also consider reachable squares occupied by an opponent builder)
     */
    public Set<Square> neighborhood(Square src) {
        Set<Square> adjacent = src.getNeighbors();
        Set<Square> neighborhood = new HashSet<>();
        for(Square square : adjacent){
            if( (square.getBuildLevel() - src.getBuildLevel()) <= 1 &&
                    square.getOccupant().isPresent() &&
                    (square.getOccupant().get().getOwner() != src.getOccupant().get().getOwner())){
                neighborhood.add(square);
            }
        }
        neighborhood.addAll(wrappedMoveBehavior.neighborhood(src));
        return neighborhood;
    }

    /**
     * @param b is the builder we want to move
     * @param dest is the square where our builder want to go
     * @return a boolean that indicates if the move phase is ended or not
     */
    public boolean move(Builder b, Square dest) {
        if(dest.getOccupant().isPresent()){
            Square start = b.getSquare();
            Builder enemy = dest.getOccupant().get();

            dest.setOccupant(b);
            b.setSquare(dest);
            start.setOccupant(enemy);
            enemy.setSquare(start);

            return false;
        } else {
            return wrappedMoveBehavior.move(b, dest);
        }
    }

    @Override
    public MoveBehavior copyBehavior(){
        return new SwapWithOpponentMove(wrappedMoveBehavior.copyBehavior());
    }
}
