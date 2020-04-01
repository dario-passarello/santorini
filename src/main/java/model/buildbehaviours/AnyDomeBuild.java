package model.buildbehaviours;

import model.Square;

import java.util.List;
import java.util.Set;

public class AnyDomeBuild implements BuildBehavior {

    /**
     * Allows the player to build either a dome or a block, no matter what the level of the square is
     * @param dest is the square the player wants to build on
     * @return true if the player can build an additional time
     */
    public boolean build(Square dest) {
        return false;
    }



    public Set<Square> neighborhood(Square src) {
        return null;
    }



}
