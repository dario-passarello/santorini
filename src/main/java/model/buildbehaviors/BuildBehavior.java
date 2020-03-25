package model.buildbehaviors;

import model.Square;

import java.util.List;

public interface BuildBehavior {
    void build();

    /**
     * @param src the position of the builder that wants to build
     * @return a list of squares where the builder can build
     */
    List<Square> neighborhood(Square src);


    void buildCoordinate(Square dest);


    boolean endBuild();
}
