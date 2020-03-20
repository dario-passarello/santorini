package model.buildbehaviors;

import model.Square;

import java.util.List;

public class StandardBuild implements BuildBehavior {
    public List<Square> neighborhood(Square src) {
        return null;
    }

    public void buildCoordinate(Square dest) {

    }

    public boolean endBuild() {
        return false;
    }
}
