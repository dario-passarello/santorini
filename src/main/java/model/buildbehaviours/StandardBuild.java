package model.buildbehaviours;

import model.Square;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StandardBuild implements BuildBehavior {



    public boolean build(Square dest) {
        return false;
    }


    public HashSet<Square> neighborhood(Square src) {
        HashSet<Square> buildable = src.getNeighbors();

        for(Square square : buildable){
            if(square.isDomed() == true) buildable.remove(square);
        }

        return buildable;
    }

}
