package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Coordinate;

public class SquareTest {
    private Board board;

    @Before
    void setUpTest() {
        board = new Board();
    }
    @Test
    void squareShouldBeCorrectlyInitialized() {
        Square sq = new Square(board, new Coordinate(1,2));
        Assert.assertSame(sq.getBoard(), board);
        Assert.assertEquals(sq.getCoordinate(), new Coordinate(2,1));
        Assert.assertNull(sq.getOccupant());
        Assert.assertEquals(sq.getBuildLevel(),0);

    }

}
