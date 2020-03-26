package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Coordinate;

public class SquareTest {
    private Board board;

    @Before
    public void setupTest() {
        board = new Board();
    }
    @Test
    public void squareShouldBeCorrectlyInitialized() {
        Square sq = new Square(board, new Coordinate(1,2));
        Assert.assertSame(sq.getBoard(), board);
        Assert.assertEquals(sq.getCoordinate(), new Coordinate(2,1));
        Assert.assertFalse(sq.getOccupant().isPresent());
        Assert.assertEquals(sq.getBuildLevel(),0);
        Assert.assertFalse(sq.isDomed());

    }
    @Test
    public void builderShouldBePlacedCorrectly() {
        Square sq = new Square(board, new Coordinate(2,3));
        Assert.assertFalse(sq.getOccupant().isPresent());
        Builder b = new Builder(sq,new Player("Test"));
        Builder c = new Builder(sq,new Player("Test2"));
        Assert.assertSame(b, sq.getOccupant().orElse(null));
        sq.setOccupant(c);
        Assert.assertSame(c, sq.getOccupant().orElse(null));
        sq.setEmptySquare();
        Assert.assertFalse(sq.getOccupant().isPresent());
        sq.setOccupant(c);
        Assert.assertSame(c, sq.getOccupant().orElse(null));
    }

    @Test
    public void squareShouldBeBuilt(){

    }
}
