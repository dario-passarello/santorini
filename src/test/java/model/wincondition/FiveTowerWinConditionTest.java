package model.wincondition;

import model.*;
import model.gods.Atlas;
import model.gods.Chronus;
import model.gods.God;
import model.gods.Mortal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

class FiveTowerWinConditionTest {

    private Player p1, p2;
    private God g1, g2;
    private Board board;
    private Game g;
    private Square[][] s;

    @BeforeEach
    public void init()
    {
        List<String> names = Arrays.asList("player1", "player2");
        try {
            g = new Game(names, 2);
        } catch (DuplicateNameException e) {
            System.err.println(e.getMessage());
        }
        board = g.getBoard();
        s = BoardTest.boardToMatrix(board);
        p1 = g.getPlayers().get(0);
        p2 = g.getPlayers().get(1);
        g1 = new Chronus();                     //they have standard win condition
        g2 = new Mortal();
        p1.setGod(g1);
        p2.setGod(g2);
        g1.setPlayer(p1);
        g2.setPlayer(p2);
        List<God> godList = Arrays.asList(g1, g2);
        g.setGodList(godList);
        g2.setWinCondition(new FiveTowerWinCondition((g2.getWinCondition()), g1));
    }

    @Test
    public void youShouldWinWith5Tower(){

        SquareTest.setSquareBuildLevel(s[1][1],3);
        s[1][1].addDome();

        Assert.assertSame(p1.getGod().checkSpecialWinCondition().orElse(null), null);
        Assert.assertSame(p2.getGod().checkSpecialWinCondition().orElse(null), null);

        SquareTest.setSquareBuildLevel(s[2][0],3);
        s[2][0].addDome();
        Assert.assertSame(p1.getGod().checkSpecialWinCondition().orElse(null), null);
        Assert.assertSame(p2.getGod().checkSpecialWinCondition().orElse(null), null);

        SquareTest.setSquareBuildLevel(s[3][3],3);
        s[3][3].addDome();
        Assert.assertSame(p1.getGod().getWinCondition().checkSpecialWinCondition(board).orElse(null), null);
        Assert.assertSame(p2.getGod().getWinCondition().checkSpecialWinCondition(board).orElse(null), null);

        SquareTest.setSquareBuildLevel(s[3][2],3);
        s[3][2].addDome();
        Assert.assertSame(p1.getGod().checkSpecialWinCondition().orElse(null), null);
        Assert.assertSame(p2.getGod().checkSpecialWinCondition().orElse(null), null);

        SquareTest.setSquareBuildLevel(s[4][0],2);              //that is not a complete tower!
        s[4][0].addDome();
        Assert.assertSame(p1.getGod().checkSpecialWinCondition().orElse(null), null);
        Assert.assertSame(p2.getGod().checkSpecialWinCondition().orElse(null), null);

        SquareTest.setSquareBuildLevel(s[4][0],1);
        Assert.assertSame(p1.getGod().checkSpecialWinCondition().orElse(null), p1);
        Assert.assertSame(p2.getGod().checkSpecialWinCondition().orElse(null), p1);

    }

    @Test
    public void youShouldHaveSimpleWinConditionToo(){
        Builder b22 = new Builder(s[2][2], p1, 1);
        Player expected = null;
        Player actual = p1.getGod().checkWinCondition(s[2][3], b22).orElse(null);
        Assert.assertEquals(expected, actual);

        SquareTest.setSquareBuildLevel(s[2][2],3);
        SquareTest.setSquareBuildLevel(s[2][3],2);
        expected = p1;
        actual = p1.getGod().checkWinCondition(s[2][3], b22).orElse(null);
        Assert.assertEquals(expected, actual);


    }
}