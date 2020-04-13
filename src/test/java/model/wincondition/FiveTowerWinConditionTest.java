package model.wincondition;

import model.*;
import model.gods.Atlas;
import model.gods.Chronus;
import model.gods.God;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

class FiveTowerWinConditionTest {

    private Player p1, p2;
    private God g1, g2;
    private Board board;
    private Game g;
    private Square[][] s;

    @Before
    public void init()
    {
        g = new Game();
        board = g.getBoard();
        s = BoardTest.boardToMatrix(board);
        p1 = new Player(g, "player1");
        p2 = new Player(g, "player2");
        g1 = new Chronus();                     //they have standard win condition
        g2 = new Atlas();
        p1.setGod(g1);
        p2.setGod(g2);
        g2.setWinCondition(new FiveTowerWinCondition((g2.getWinCondition()), g1));
    }

    @Test
    public void test(){

        SquareTest.setSquareBuildLevel(s[1][1],3);
        s[1][1].addDome();
        Assert.assertSame(p1.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), null);
        Assert.assertSame(p2.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), null);

        SquareTest.setSquareBuildLevel(s[2][0],3);
        s[2][0].addDome();
        Assert.assertSame(p1.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), null);
        Assert.assertSame(p2.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), null);

        SquareTest.setSquareBuildLevel(s[3][3],3);
        s[3][3].addDome();
        Assert.assertSame(p1.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), null);
        Assert.assertSame(p2.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), null);
        SquareTest.setSquareBuildLevel(s[3][2],3);
        s[3][2].addDome();
        Assert.assertSame(p1.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), null);
        Assert.assertSame(p2.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), null);

        SquareTest.setSquareBuildLevel(s[4][0],3);
        s[4][0].addDome();
        Assert.assertSame(p1.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), p1);
        Assert.assertSame(p2.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), p1);

        SquareTest.setSquareBuildLevel(s[4][0],2);

        Assert.assertSame(p1.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), null);
        Assert.assertSame(p2.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), null);

    }
}