package model.buildbehaviors;

import model.Square;

import java.util.ArrayList;

import model.SquareTest;

public class FakeStandardBuild extends StandardBuild {

    GameDriver game;

    @Override
    public ArrayList<Square> neighborhood(Square square){

        ArrayList<Square> result = new ArrayList<>();

        Square square1 = game.getSquare(4, 3);
        Square square2 = game.getSquare(4, 4);
        Square square3 = game.getSquare(5, 5);

        result.add(square1);
        result.add(square2);
        result.add(square3);

        return result;
    }


    @Override
    public void build(Square dest) {

        if (dest.getBuildLevel() == 3) dest.addDome();
        else {
            SquareTest.setSquareBuildLevel(dest,dest.getBuildLevel() + 1);
        }
    }

}
