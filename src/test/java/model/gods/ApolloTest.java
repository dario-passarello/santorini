package model.gods;

import model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;


class ApolloTest {


    private Player p1, p2, p3;
    private God g1, g2;
    private Board board;
    private Game g;
    private Square[][] s;

    @Before
    public void setUpTest(){
        g = new Game();
        board = g.getBoard();
        s = BoardTest.boardToMatrix(board);
        p1 = new Player(g,"player1");
        p2 = new Player(g,"player2");
        p3 = new Player(g,"player3");
        g1 = new Apollo();
        g2 = new Atlas();
        p1.setGod(g1);
        p2.setGod(g2);
    }

    /**
     * this test checks if the Apollo's move behavior works
     */
    @Test
    public void canUsePower(){
        //creating some buildings
        SquareTest.setSquareBuildLevel(s[3][3],0);
        SquareTest.setSquareBuildLevel(s[2][2],0);
        SquareTest.setSquareBuildLevel(s[2][3],1);
        SquareTest.setSquareBuildLevel(s[2][4],2);
        SquareTest.setSquareBuildLevel(s[3][4],3);
        SquareTest.setSquareBuildLevel(s[4][4],0);
        SquareTest.setSquareBuildLevel(s[4][3],1);
        SquareTest.setSquareBuildLevel(s[4][2],2);
        SquareTest.setSquareBuildLevel(s[3][2],3);

        //placing some builder
        Builder b33 = new Builder(s[3][3], p1);                // b33 is the builder that is going to move
        Builder b22 = new Builder(s[2][2], p2);
        Builder b23 = new Builder(s[2][3], p2);
        Builder b24 = new Builder(s[2][4], p2);
        Builder b34 = new Builder(s[3][4], p2);
        Builder b44 = new Builder(s[4][4], p1);
        Builder b43 = new Builder(s[4][3], p1);
        Builder b42 = new Builder(s[4][2], p1);
        Builder b32 = new Builder(s[3][2], p1);

        List<Square> expectedList = Arrays.asList(s[2][2],s[2][3]);   //b1 should be able to move only on these squares
        Assert.assertEquals(expectedList, b33.getBuildableNeighborhood());

        b33.move(board.squareAt(2,2));

        Assert.assertSame(s[3][3].getOccupant().orElse(null),b22);
        Assert.assertSame(s[2][2].getOccupant().orElse(null),b33);

    }


}