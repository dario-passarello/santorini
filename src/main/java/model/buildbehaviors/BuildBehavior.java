package model.buildbehaviors;

import model.Square;

import java.util.List;

public interface BuildBehavior {
    void build();
    List<Square> neighborhood(Square src);
    void buildCoordinate(Square dest);
    boolean endBuild();
}
