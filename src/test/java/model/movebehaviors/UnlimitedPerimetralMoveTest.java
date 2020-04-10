package model.movebehaviors;

import model.*;
import model.gods.God;
import model.gods.Triton;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnlimitedPerimetralMoveTest {


    private Player p1;
    private God g1;
    private Board board;
    private Game g;
    private Square[][] s;

    @Before
    public void setUpTest(){
        g = new Game();
        board = g.getBoard();
        s = BoardTest.boardToMatrix(board);
        p1 = new Player(g,"player1");
        g1 = new Triton();
        p1.setGod(g1);
    }

    @Test
    public void youCanContinueAfterPerimeter(){

        Builder b13 = new Builder(board.squareAt(1,3), p1);
        Assert.assertTrue(b13.move(board.squareAt(0,4)));           //from center to perimeter
        Assert.assertTrue(b13.move(board.squareAt(1,4)));           //from perimeter to perimeter
        Assert.assertFalse(b13.move(board.squareAt(1,3)));          //from perimeter to center
        Assert.assertFalse(b13.move(board.squareAt(2,3)));           //from center to center
    }

}