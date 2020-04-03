package model.buildbehaviours;

import model.Board;
import model.Square;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DoubleNoPerimeterBuild extends BuildDecorator{

    // Keeps track of the order of the builds
    private boolean second;
    private BuildBehavior wrappedBuildBehavior;


    public boolean build(Square dest) {
        return false;
    }

    /**
     * the first iteration of the method is exactly like the StandardBuild
     * the second iteration of the method returns only non-perimetral squares
     * @param src the position of the builder that wants to build
     * @return
     */
    public HashSet<Square> neighborhood(Square src) {
        if(second == false) return this.wrappedBuildBehavior.neighborhood(src);
        else{
            HashSet<Square> buildable = wrappedBuildBehavior.neighborhood(src);
            for(Square square : buildable){
                if((square.getCoordinate().getY() != Board.BOARD_SIZE) &&
                    square.getCoordinate().getX() != Board.BOARD_SIZE) buildable.remove(square);
            }
            return buildable;
        }
    }

    public DoubleNoPerimeterBuild(BuildBehavior buildBehavior){
        this.wrappedBuildBehavior = buildBehavior;
        this.second = false;
    }


}
