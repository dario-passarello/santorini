package model.movebehaviors;

import model.*;
import model.gods.Artemis;
import model.gods.Atlas;
import model.gods.God;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class DoubleNoBackMoveTest {

    private Player p1, p2;
    private God g1, g2;
    private Board board;
    private Game g;
    private Square[][] s;

    @Before
    public void init(){
        g = new Game();
        board = g.getBoard();
        s = BoardTest.boardToMatrix(board);
        p1 = new Player(g, "player1");
        p2 = new Player(g, "player2");
        g1 = new Artemis();                         //Pan has a standard move behavior
        g2 = new Atlas();
        p1.setGod(g1);
        p2.setGod(g2);
    }

    @Test
    public void youShouldNotComeBack(){
        Builder b22 = new Builder(s[2][2], p1);

        List<Square> expectedList = Arrays.asList(  s[1][1], s[1][2],s[1][3],
                                                    s[2][1], s[2][3],
                                                    s[3][1], s[3][2], s[3][3]);
        Assert.assertEquals(expectedList, b22.getWalkableNeighborhood());

        Assert.assertTrue(b22.move(s[1][2]));

        expectedList = Arrays.asList(  s[0][1], s[0][2],s[0][3],
                                       s[1][1], s[1][3],
                                       s[2][1], s[2][3]);
        Assert.assertEquals(expectedList, b22.getWalkableNeighborhood());

        Assert.assertFalse(b22.move(s[1][3]));
    }

}