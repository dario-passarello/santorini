package model.buildbehaviours;

import model.Square;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StandardBuild implements BuildBehavior {



    public boolean build(Square dest) {
        dest.build();
        return false;
    }


    public Set<Square> neighborhood(Square src) {
        Set<Square> buildable = src.getNeighbors();
        Set<Square> removable = new HashSet<>();
        for(Square square : buildable){
            if(square.isDomed() == true || square.getOccupant().isPresent()) removable.add(square);
        }

        buildable.removeAll(removable);

        return buildable;
    }

}
