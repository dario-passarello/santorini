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

    @Before
    public void init()
    {
        g = new Game();
        board = g.getBoard();
        p1 = new Player(g, "player1");
        p2 = new Player(g, "player2");
        g1 = new Chronus(p1);                     //they have standard win condition
        g2 = new Atlas(p2);
        g2.setWinCondition(new FiveTowerWinCondition((g2.getWinCondition()), p1));
    }

    @Test
    public void test(){

        Square s11 = board.squareAt(1,1);
        Square s20 = board.squareAt(2,0);
        Square s33 = board.squareAt(3,3);
        Square s32 = board.squareAt(3,2);
        Square s40 = board.squareAt(4,0);

        SquareTest.setSquareBuildLevel(s11,3);
        s11.addDome();
        Assert.assertSame(p1.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), null);
        Assert.assertSame(p2.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), null);

        SquareTest.setSquareBuildLevel(s20,3);
        s20.addDome();
        Assert.assertSame(p1.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), null);
        Assert.assertSame(p2.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), null);

        SquareTest.setSquareBuildLevel(s33,3);
        s33.addDome();
        Assert.assertSame(p1.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), null);
        Assert.assertSame(p2.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), null);
        SquareTest.setSquareBuildLevel(s32,3);
        s32.addDome();
        Assert.assertSame(p1.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), null);
        Assert.assertSame(p2.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), null);

        SquareTest.setSquareBuildLevel(s40,3);
        s40.addDome();
        Assert.assertSame(p1.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), p1);
        Assert.assertSame(p2.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), p1);

        SquareTest.setSquareBuildLevel(s40,2);

        Assert.assertSame(p1.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), null);
        Assert.assertSame(p2.getGod().getWinCondition().checkSpecialWinCondition().orElse(null), null);

    }
}