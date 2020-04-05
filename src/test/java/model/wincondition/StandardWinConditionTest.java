package model.wincondition;

import model.*;
import model.Board;
import model.Player;
import model.gods.Atlas;
import model.gods.Demeter;
import model.gods.God;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

class StandardWinConditionTest {

    private Player p1, p2;
    private God g1, g2;
    private Board board;
    private Builder b1;
    private Game g;

    @Before
    public void init()
    {
        g = new Game();
        board = g.getBoard();
        p1 = new Player(g, "player1");
        p2 = new Player(g, "player2");
        g1 = new Demeter(p1);                     //they have standard win condition
        g2 = new Atlas(p2);
    }

    @Test
    public void youShouldWin(){
        Player expectedWinner = p1;

        Square s33 = board.squareAt(3,3);
        Square s32 = board.squareAt(3,2);
        SquareTest.setSquareBuildLevel(s33,3);
        SquareTest.setSquareBuildLevel(s32,2);
        Square start = s32;
        b1 = p1.addBuilder(s33);
        Assert.assertSame(p1.getGod().getWinCondition().checkWinCondition(start,b1).orElse(null), expectedWinner);

    }

    @Test
    public void youShouldNotWin(){
        Player expectedWinner = null;

        Square s33 = board.squareAt(3,3);
        Square s32 = board.squareAt(3,2);
        SquareTest.setSquareBuildLevel(s33,3);
        SquareTest.setSquareBuildLevel(s32,3);
        Square start = s32;
        b1 = p1.addBuilder(s33);
        Assert.assertSame(p1.getGod().getWinCondition().checkWinCondition(start,b1).orElse(null), expectedWinner);
    }
}