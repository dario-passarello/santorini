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
    private Square[][] s;

    @Before
    public void init()
    {
        g = new Game();
        board = g.getBoard();
        s = BoardTest.boardToMatrix(board);
        p1 = new Player(g, "player1");
        p2 = new Player(g, "player2");
        g1 = new Hera();                     //she applies this malus to the win condition
        g2 = new Atlas();
        p1.setGod(g1);
        p2.setGod(g2);
        g2.setWinCondition(new NoPerimeterWinCondition(g2.getWinCondition()));
    }

    @Test
    public void youShouldNotWin(){
        Player expectedWinner = null;
        SquareTest.setSquareBuildLevel(s[4][1],3);
        SquareTest.setSquareBuildLevel(s[3][2],2);
        Square start = s[3][2];
        b2 = p2.addBuilder(s[4][1]);
        Assert.assertSame(p2.getGod().getWinCondition().checkWinCondition(start,b2).orElse(null), expectedWinner);
    }

}