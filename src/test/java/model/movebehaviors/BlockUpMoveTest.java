package model.movebehaviors;

import model.*;
import model.gods.Athena;
import model.gods.Atlas;
import model.gods.God;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class BlockUpMoveTest {

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
        g1 = new Athena();
        g2 = new Atlas();
        p1.setGod(g1);
        p2.setGod(g2);
        List<God> godList = Arrays.asList(g1, g2);
        g.setGodList(godList);
    }

    //here we test BlockUpMove and NoUpMove all at once
    @Test
    public void enemyShouldNotMoveUp(){
        Builder b01 = new Builder(s[0][1], p1);
        Builder b44 = new Builder(s[4][4], p2);

        SquareTest.setSquareBuildLevel(s[0][0], 1);
        SquareTest.setSquareBuildLevel(s[3][3], 1);

        b01.move(s[0][0]);
        List<Square> expectedList = Arrays.asList(s[4][3], s[3][4]);
        Assert.assertEquals(expectedList, b44.getWalkableNeighborhood());
    }
}