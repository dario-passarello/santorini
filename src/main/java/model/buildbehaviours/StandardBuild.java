package model.buildbehaviours;

import model.Square;

import java.util.List;
import java.util.Set;

public class StandardBuild implements BuildBehavior {


    public boolean build(Square dest) {
        return false;
    }


    public Set<Square> neighborhood(Square src) {
        return null;
    }

}
