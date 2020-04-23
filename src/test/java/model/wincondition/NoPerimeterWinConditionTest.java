package model.wincondition;

import model.*;
import model.gods.Atlas;
import model.gods.God;
import model.gods.Hera;
import model.gods.Mortal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
        g1 = new Hera();                     //she applies this malus to the win condition
        g2 = new Mortal();
        p1.setGod(g1);
        p2.setGod(g2);
        List<God> godList = Arrays.asList(g1, g2);
        g.setGodList(godList);
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