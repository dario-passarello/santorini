package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Coordinate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SquareTest {
    private Game game;
    private Board board;

    @Before
    public void setupTest() {

        List<String> names = Arrays.asList("player1", "player2");
        try {
            game = new Game(names, 2);
        } catch (DuplicateNameException e) {
            System.err.println(e.getMessage());
        }
        board = game.getBoard();
    }

    @Test
    public void squareShouldBeCorrectlyInitialized() {
        Square sq = new Square(board, new Coordinate(1,2));
        Assert.assertSame(sq.getBoard(), board);
        Assert.assertEquals(sq.getCoordinate(), new Coordinate(1,2));
        Assert.assertFalse(sq.getOccupant().isPresent());
        Assert.assertEquals(sq.getBuildLevel(),0);
        Assert.assertFalse(sq.isDomed());

    }

    @Test
    public void builderShouldBePlacedCorrectly() {
        Square sq = new Square(board, new Coordinate(2,3));
        Assert.assertFalse(sq.getOccupant().isPresent());
        Builder b = new Builder(sq,new Player(game, "Test"), 1);
        Assert.assertSame(b, sq.getOccupant().orElse(null));
        Builder c = new Builder(sq,new Player(game, "Test2"), 1);
        Assert.assertSame(c, sq.getOccupant().orElse(null));
        sq.setOccupant(b);
        Assert.assertSame(b, sq.getOccupant().orElse(null));
        sq.setEmptySquare();
        Assert.assertFalse(sq.getOccupant().isPresent());
        sq.setOccupant(c);
        Assert.assertSame(c, sq.getOccupant().orElse(null));
    }

    @Test
    public void squareShouldBeBuiltCorrectly(){
        for(int i = 0; i < Square.MAX_HEIGHT; i++) {
            Square sq = new Square(board,new Coordinate(2,1));
            for(int j = 0; j < i; j++) {
                Assert.assertEquals("Square should be at level " + j,sq.getBuildLevel(), j);
                Assert.assertTrue("Square at level " + j + "should be buildable",sq.isBuildable());
                sq.build();
            }

            Assert.assertEquals("Square should be at level " + i,sq.getBuildLevel(), i);
            Assert.assertTrue("Square at level " + i + "should be buildable",sq.isBuildable());
            Assert.assertFalse("Square at level " + i + "should not be domed",sq.isDomed());
            sq.addDome();
            Assert.assertTrue(sq.isDomed());
            Assert.assertFalse(sq.isBuildable());

        }

    }

    @Test
    public void perimetralConditionShouldBeCorrect(){
        for(int i = 0; i < Board.BOARD_SIZE; i++){
            for(int j = 0; j < Board.BOARD_SIZE; j++){
                if(i == 0 || i == 4 || j == 0  || j == 4){
                    assertTrue(board.squareAt(i,j).isPerimetral());
                } else {
                    assertFalse(board.squareAt(i,j).isPerimetral());
                }
            }
        }

    }

    public static void setSquareBuildLevel(Square sq, int level){
        for(int i = 0; i < level; i++){
            sq.build();
        }
    }
}
