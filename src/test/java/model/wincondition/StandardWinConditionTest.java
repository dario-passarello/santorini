package model.wincondition;

import model.*;
import model.Board;
import model.Player;
import model.gods.Atlas;
import model.gods.Demeter;
import model.gods.God;
import model.gods.Mortal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class StandardWinConditionTest {

    private Player p1, p2;
    private God g1, g2;
    private Board board;
    private Builder b1;
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
        g1 = new Mortal();                     //they have standard win condition
        g2 = new Mortal();
        p1.setGod(g1);
        p2.setGod(g2);
        g1.setPlayer(p1);
        g2.setPlayer(p2);
        List<God> godList = Arrays.asList(g1, g2);
        g.setGodList(godList);
    }

    @Test
    public void youShouldWin(){
        Player expectedWinner = p1;

        SquareTest.setSquareBuildLevel(s[3][3],3);
        SquareTest.setSquareBuildLevel(s[3][2],2);
        Square start = s[3][2];
        b1 = new Builder(s[3][3], p1, 1);
        Assert.assertSame(p1.getGod().getWinCondition().checkWinCondition(start,b1).orElse(null), expectedWinner);

    }

    @Test
    public void youShouldNotWin(){
        Player expectedWinner = null;

        SquareTest.setSquareBuildLevel(s[3][3],3);
        SquareTest.setSquareBuildLevel(s[3][2],3);
        Square start = s[3][2];
        b1 = new Builder(s[3][3], p1, 1);
        //b1 = p1.addBuilder(s[3][3]);
        Assert.assertSame(p1.getGod().checkWinCondition(start,b1).orElse(null), expectedWinner);
    }

    @Test
    public void specialWinConditionIsEmpty(){
        Assert.assertEquals(null, p1.getGod().checkSpecialWinCondition().orElse(null));
    }
}