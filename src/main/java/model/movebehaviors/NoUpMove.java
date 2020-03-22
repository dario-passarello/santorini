package model.movebehaviors;

import model.Square;

import java.util.List;
//TODO
public class NoUpMove extends MoveDecorator{

    public NoUpMove(MoveBehavior moveBehavior){
        setWrappedBehavior(moveBehavior);
    }

    public List<Square> neighborhood(Square src) {
        return null;
    }

    public void move(Square dest) {

    }

    public boolean endMove() {
        return false;
    }
}
