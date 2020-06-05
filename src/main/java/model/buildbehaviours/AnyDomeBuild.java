package model.buildbehaviours;

import model.Square;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnyDomeBuild extends BuildDecorator {

    /**
     * Allows the player to build either a dome or a block, no matter what the level of the square is
     * @param dest is the square the player wants to build on
     * @return true if the player can build an additional time
     */
    public boolean build(Square dest) {
        return false;
    }

    public Set<Square> neighborhood(Square src) {
        return wrappedBuildBehavior.neighborhood(src);
    }


    public AnyDomeBuild(BuildBehavior buildBehavior){
        this.wrappedBuildBehavior = buildBehavior;
    }



}
