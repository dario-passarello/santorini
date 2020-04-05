package model.wincondition;

import model.*;
import model.gods.Atlas;
import model.gods.God;
import model.gods.Hera;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

class NoPerimeterWinConditionTest {

    private Player p1, p2;
    private God g1, g2;
    private Board board;
    private Builder b2;
    private Game g;

    @Before
    public void init()
    {
        g = new Game();
        board = g.getBoard();
        p1 = new Player(g, "player1");
        p2 = new Player(g, "player2");
        g1 = new Hera(p1);                     //she applies this malus to the win condition
        g2 = new Atlas(p2);
        g2.setWinCondition(new NoPerimeterWinCondition(g2.getWinCondition()));
    }

    @Test
    public void youShouldNotWin(){
        Player expectedWinner = null;
        Square s41 = board.squareAt(3,3);
        Square s32 = board.squareAt(3,2);
        SquareTest.setSquareBuildLevel(s41,3);
        SquareTest.setSquareBuildLevel(s32,2);
        Square start = s32;
        b2 = p2.addBuilder(s41);
        Assert.assertSame(p2.getGod().getWinCondition().checkWinCondition(start,b2).orElse(null), expectedWinner);
    }

}