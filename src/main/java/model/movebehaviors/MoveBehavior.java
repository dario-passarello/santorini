package model.movebehaviors;

import model.Square;

import java.util.List;
//TODO
public interface MoveBehavior {
    List<Square> neighborhood(Square src);
    void move(Square dest);
    boolean endMove();
}
