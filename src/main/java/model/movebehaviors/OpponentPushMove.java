package model.movebehaviors;

import model.Board;
import model.Builder;
import model.Square;

import java.util.HashSet;
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
    public Set<Square> neighborhood(Square src) {
        Set<Square> adjacent = src.getNeighbors();
        Set<Square> neighborhood = new HashSet<>();

        int srcX, srcY, squareX, squareY, dirX,dirY, pushX, pushY;
        Square push;
        srcX = src.getCoordinate().getX();
        srcY = src.getCoordinate().getY();
        for(Square square : adjacent){
            if( (square.getBuildLevel() - src.getBuildLevel()) <= 1 &&                                      //if it's reachable
                    !square.isDomed() &&
                     square.getOccupant().isPresent() &&                                                   //and there is another builder
                    (square.getOccupant().get().getOwner() != src.getOccupant().get().getOwner())){        // that is not mine

                squareX = square.getCoordinate().getX();
                squareY = square.getCoordinate().getY();
                dirX = squareX - srcX;
                dirY = squareY - srcY;
                pushX = squareX + dirX;
                pushY = squareY + dirY;

                if(pushX >= 0 && pushX <= Board.BOARD_SIZE-1 && pushY >= 0 && pushY <= Board.BOARD_SIZE-1) {     //check if I can push him

                    push = square.getBoard().squareAt(pushX, pushY);
                    //System.out.println("["+push.getCoordinate().getX()+","+push.getCoordinate().getY()+"]");

                    if (push.getBuildLevel() - square.getBuildLevel() <= 1 &&
                            !push.isDomed() &&
                            !push.getOccupant().isPresent()) {
                        neighborhood.add(square);
                    }
                }
            }
        }

        neighborhood.addAll(wrappedMoveBehavior.neighborhood(src));
        return neighborhood;
    }


    /**
     * @param b is the builder we want to move
     * @param dest is the square where our builder want to go
     * @return a boolean that indicates if the move phase is ended or not
     *
     * if b goes to a square occupied by an opponent builder, he will push him in the same direction
     */
    public boolean move(Builder b, Square dest) {

        if(dest.getOccupant().isPresent()){
            Square src = b.getPosition();                       //starting position
            int srcX = src.getCoordinate().getX();              //his coordinate
            int srcY = src.getCoordinate().getY();
            int destX = dest.getCoordinate().getX();            //dest coordinate
            int destY = dest.getCoordinate().getY();
            int dirX = destX - srcX;                            //push direction
            int dirY = destY - srcY;
            int pushX = destX + dirX;                           //push coordinate
            int pushY = destY + dirY;

            Board board = dest.getBoard();
            Square push = board.squareAt(pushX, pushY);          //push square
            Builder enemy = dest.getOccupant().get();

            push.setOccupant(dest.getOccupant().get());
            enemy.setPosition(push);

            dest.setOccupant(b);
            b.setPosition(dest);

            src.setOccupant(null);

            return false;
        } else {
            return wrappedMoveBehavior.move(b,dest);
        }
    }

}
