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

    @Before
    public void setUpTest(){
        g = new Game();
        board = g.getBoard();
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

        Square s33 = board.squareAt(3,3);
        Square s22 = board.squareAt(2,2);
        Square s23 = board.squareAt(2,3);
        Square s24 = board.squareAt(2,4);
        Square s34 = board.squareAt(3,4);
        Square s44 = board.squareAt(4,4);
        Square s43 = board.squareAt(4,3);
        Square s42 = board.squareAt(4,2);
        Square s32 = board.squareAt(3,2);
        //creating some buildings

        SquareTest.setSquareBuildLevel(s33,0);
        SquareTest.setSquareBuildLevel(s22,0);
        SquareTest.setSquareBuildLevel(s23,1);
        SquareTest.setSquareBuildLevel(s24,2);
        SquareTest.setSquareBuildLevel(s34,3);
        SquareTest.setSquareBuildLevel(s44,0);
        SquareTest.setSquareBuildLevel(s43,1);
        SquareTest.setSquareBuildLevel(s42,2);
        SquareTest.setSquareBuildLevel(s32,3);

        //placing some builder
        Builder b33 = new Builder(s33, p1);                // b33 is the builder that is going to move
        Builder b22 = new Builder(s22, p2);
        Builder b23 = new Builder(s23, p2);
        Builder b24 = new Builder(s24, p2);
        Builder b34 = new Builder(s34, p2);
        Builder b44 = new Builder(s44, p1);
        Builder b43 = new Builder(s43, p1);
        Builder b42 = new Builder(s42, p1);
        Builder b32 = new Builder(s32, p1);

        List<Square> expectedList = Arrays.asList(s22,s23);   //b1 should be able to move only on these squares
        Assert.assertEquals(expectedList, b33.getBuildableNeighborhood());

        b33.move(board.squareAt(2,2));

        Assert.assertSame(s33.getOccupant().orElse(null),b22);
        Assert.assertSame(s22.getOccupant().orElse(null),b33);

    }


}