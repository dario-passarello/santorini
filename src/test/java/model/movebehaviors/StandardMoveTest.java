package model.movebehaviors;

import model.*;
import model.gods.Atlas;
import model.gods.God;
import model.gods.Pan;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

class StandardMoveTest {

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
        g1 = new Pan();                         //Pan has a standard move behavior
        g2 = new Atlas();
        p1.setGod(g1);
        p2.setGod(g2);
    }

    @Test
    public void simpleNeighborhoodTest(){
        Builder b22 = new Builder(s[2][2], p1);     //full neighborhood
        Builder b14 = new Builder(s[1][4], p1);     //edge neighborhood
        Builder b44 = new Builder(s[4][4], p1);     //corner neighborhood

        List<Square> expectedList = Arrays.asList(  s[1][1], s[1][2],s[1][3],
                                                    s[2][1], s[2][3],
                                                    s[3][1], s[3][2], s[3][3]);
        Assert.assertEquals(expectedList, b22.getWalkableNeighborhood());

        expectedList = Arrays.asList(s[0][3], s[0][4], s[1][3], s[2][3], s[2][4]);
        Assert.assertEquals(expectedList, b14.getWalkableNeighborhood());

        expectedList = Arrays.asList(s[3][3], s[3][4], s[4][3]);
        Assert.assertEquals(expectedList, b44.getWalkableNeighborhood());
    }

    public void hinderedNeighborhoodTest(){
        Builder b33 = new Builder(s[2][2], p1);
        //three blocks with just buildings
        SquareTest.setSquareBuildLevel(s[1][1],1);
        SquareTest.setSquareBuildLevel(s[1][2],2);
        SquareTest.setSquareBuildLevel(s[1][3],3);

        //three blocks with buildings and domes
        SquareTest.setSquareBuildLevel(s[3][1],0);
        SquareTest.setSquareBuildLevel(s[3][2],1);
        SquareTest.setSquareBuildLevel(s[3][3],2);
        s[3][1].addDome();
        s[3][2].addDome();
        s[3][3].addDome();

        //two buildings with other builder (friend or enemy)
        SquareTest.setSquareBuildLevel(s[2][1],0);
        SquareTest.setSquareBuildLevel(s[2][3],1);
        Builder b42 = new Builder(s[2][1], p1);
        Builder b32 = new Builder(s[2][3], p2);

        List<Square> expectedList = Arrays.asList(s[1][1]);
        Assert.assertEquals(expectedList, b33.getWalkableNeighborhood());
    }

    public void moveTest(){
        Builder b22 = new Builder(s[2][2], p1);

        b22.move(s[3][3]);

        Assert.assertSame(s[3][3].getOccupant().orElse(null), b22);
        Assert.assertSame(s[2][2].getOccupant().orElse(null), null);

    }

}