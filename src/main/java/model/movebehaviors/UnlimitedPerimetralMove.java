package model.movebehaviors;

import model.Square;

import java.util.List;

public class UnlimitedPerimetralMove extends MoveDecorator {

    public UnlimitedPerimetralMove(MoveBehavior moveBehavior){
        wrappedMoveBehavior = moveBehavior;
    }

    public List<Square> neighborhood(Square src) {
        //TODO
        return null;
    }

    public void move(Square dest) {

    }

    public boolean endMove() {
        return false;
    }
}
