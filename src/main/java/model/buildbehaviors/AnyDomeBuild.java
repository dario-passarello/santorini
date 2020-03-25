package model.buildbehaviors;

import model.Square;

import java.util.List;

public class AnyDomeBuild implements BuildBehavior {

    public void build(Square dest) {

    }

    private void buildDome(Square dest) {

    }

    public List<Square> neighborhood(Square src) {
        return null;
    }

    public boolean endBuild() {
        return false;
    }

}
