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
    private Square[][] s;

    @Before
    public void init()
    {
        g = new Game();
        board = g.getBoard();
        s = BoardTest.boardToMatrix(board);
        p1 = new Player(g, "player1");
        p2 = new Player(g, "player2");
        g1 = new Demeter();                     //they have standard win condition
        g2 = new Atlas();
        p1.setGod(g1);
        p2.setGod(g2);
    }

    @Test
    public void youShouldWin(){
        Player expectedWinner = p1;

        SquareTest.setSquareBuildLevel(s[3][3],3);
        SquareTest.setSquareBuildLevel(s[3][2],2);
        Square start = s[3][2];
        b1 = p1.addBuilder(s[3][3]);
        Assert.assertSame(p1.getGod().getWinCondition().checkWinCondition(start,b1).orElse(null), expectedWinner);

    }

    @Test
    public void youShouldNotWin(){
        Player expectedWinner = null;

        SquareTest.setSquareBuildLevel(s[3][3],3);
        SquareTest.setSquareBuildLevel(s[3][2],3);
        Square start = s[3][2];
        b1 = p1.addBuilder(s[3][3]);
        Assert.assertSame(p1.getGod().getWinCondition().checkWinCondition(start,b1).orElse(null), expectedWinner);
    }
}