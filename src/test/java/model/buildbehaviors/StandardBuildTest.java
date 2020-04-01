package model.buildbehaviors;

import model.Square;
import model.SquareTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;


public class StandardBuildTest {

    public static GameDriver game = new GameDriver();
    FakeStandardBuild fake;
    StandardBuild standardbuildtest;

    @Before
    public void init(){
        game.start();
        game.setbuilder(4, 5);
        game.setdome(5, 3);


    }




    @Test
    public void NeighborhoodTest(){

        fake = new FakeStandardBuild();

        Square SquareTest = game.getSquare(5, 4);
        ArrayList<Square> Neighbours = fake.neighborhood(SquareTest);


        Assert.assertNotNull("The Square should be on the Board",
                game.board.getMatrix().contains(SquareTest));

        Assert.assertTrue("All the neighbours should be on the board",
                Neighbours.stream().anyMatch(Square -> game.board.getMatrix().stream().anyMatch(list -> list.contains(Square))));
        
        Assert.assertFalse("The Square is not a neighbour of itself", 
                Neighbours.contains(SquareTest));

        Assert.assertTrue("Some of the squares might be too distant",
                Neighbours.stream().allMatch(Square ->  (Math.abs(Square.getCoordinate().getX() - SquareTest.getCoordinate().getX()) <= 1
                                                      && Math.abs(Square.getCoordinate().getY() - SquareTest.getCoordinate().getY()) <= 1)));

       Assert.assertTrue("Buildable Neighbour squares can't have workers on them",
                Neighbours.stream().noneMatch(Square -> Square.getOccupant().isPresent()));

        Assert.assertTrue("Buildable Neighbour squares can't have the dome",
                Neighbours.stream().noneMatch(Square -> Square.isDomed()));


    }

    @Test
    public void BuildTest(){

        fake = new FakeStandardBuild();

        Square squareStart = game.getSquare(4, 4);
        Square oldSquare = new Square(game.board, squareStart.getCoordinate());   // Previous Instance of Square (Artificially created)

        SquareTest.setSquareBuildLevel(squareStart,3);
        SquareTest.setSquareBuildLevel(oldSquare,3);


        fake.build(squareStart);



        Assert.assertTrue("The Square should be on the board",
                game.board.getMatrix().stream().anyMatch(List -> List.contains(squareStart)));

        Assert.assertFalse("The Square should not contain the dome",
                oldSquare.isDomed());

        Assert.assertTrue("The Square should either have a built block or a built dome",
                        ((squareStart.getBuildLevel() == oldSquare.getBuildLevel() + 1) ||
                        ((squareStart.getBuildLevel() == oldSquare.getBuildLevel()) && squareStart.isDomed())));
    }
}
