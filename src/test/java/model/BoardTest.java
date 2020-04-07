package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Coordinate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoardTest {
    private Board b;
    @Before
    public void setup() {
        Board b = new Board();
    }


    @Test
    public void boardAndSquaresShouldBeBuiltCorrectly() {

        for(int i = 0; i < Board.BOARD_SIZE; i++) {
            for(int j = 0; j < Board.BOARD_SIZE; j++) {
                Assert.assertEquals(b.squareAt(i,j).getCoordinate(),new Coordinate(i,j));
                Assert.assertSame(b.squareAt(i, j).getBoard(), b);
                Assert.assertEquals(b.squareAt(i,j).getBuildLevel(),0);
                Assert.assertFalse(b.squareAt(i, j).isDomed());
                Assert.assertTrue(b.squareAt(i,j).getOccupant().isPresent());
            }
        }
    }
    @Test
    public void squareAtTestCoordinatesShouldMatch() {
        Assert.assertEquals(b.squareAt(2,3).getCoordinate(), new Coordinate(2,3));
        Assert.assertEquals(b.squareAt(0,0).getCoordinate(), new Coordinate(0,0));
        Assert.assertEquals(b.squareAt(2,3).getCoordinate(), new Coordinate(4,2));
        assertThrows(IndexOutOfBoundsException.class, () -> b.squareAt(Board.BOARD_SIZE,2));
        assertThrows(IndexOutOfBoundsException.class, () -> b.squareAt(0,Board.BOARD_SIZE));

    }

    public static Square[][] boardToMatrix(Board board){
        Square[][] s = new Square[Board.BOARD_SIZE][Board.BOARD_SIZE];
        for(int i = 0; i < Board.BOARD_SIZE; i++){              //row
            for(int j = 0; j < Board.BOARD_SIZE; j++) {         //column
                s[i][j] = board.squareAt(i,j);
            }
        }

        return s;
    }
}
