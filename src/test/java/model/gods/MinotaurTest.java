package model.gods;

import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MinotaurTest {

    private Player p1, p2, p3;
    private God g1, g2;
    private Board board;

    @Before
    public void setUpTest(){
        p1 = new Player("player1");
        p2 = new Player("player2");
        p3 = new Player("player3");
        g1 = new Minotaur();
        g2 = new Atlas();
        p1.setGod(g1);
        p2.setGod(g2);
        board = new Board();
    }

    @Test
    public void canUsePower(){

        Square s11 = board.squareAt(1,1);
        Square s33 = board.squareAt(3,3);       //s33 is the initial position of Minotaur
        Square s22 = board.squareAt(2,2);
        Square s23 = board.squareAt(2,3);
        Square s24 = board.squareAt(2,4);
        Square s34 = board.squareAt(3,4);
        Square s44 = board.squareAt(4,4);
        Square s43 = board.squareAt(4,3);
        Square s42 = board.squareAt(4,2);
        Square s32 = board.squareAt(3,2);
        //creating some buildings
        SquareTest.setSquareBuildLevel(s23,1);
        SquareTest.setSquareBuildLevel(s24,2);
        SquareTest.setSquareBuildLevel(s34,3);
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

        List<Square> expectedList = (List<Square>) Arrays.asList(s22,s23);   //b1 should be able to move only on these squares
        Assert.assertEquals(expectedList, b33.getBuildableNeighborhood());

        b33.move(board.squareAt(2,2));          //Minotaur push an opponent builder in (1,1)
        Assert.assertEquals(s33.getOccupant(), null);
        Assert.assertEquals(s22.getOccupant(), b33);
        Assert.assertEquals(s11.getOccupant(), b22);
    }

    public void cannotUsePower(){

        Square s23 = board.squareAt(2,2);       //s33 is the initial position of Minotaur
        Square s12 = board.squareAt(1,1);
        Square s13 = board.squareAt(3,3);
        Square s14 = board.squareAt(2,3);
        Square s24 = board.squareAt(2,4);
        Square s34 = board.squareAt(3,4);
        Square s33 = board.squareAt(4,4);
        Square s32 = board.squareAt(4,3);
        Square s22 = board.squareAt(4,2);
        //"Blocking" squares
        Square s21 = board.squareAt(2,1);
        Square s43 = board.squareAt(4,3);
        Square s25 = board.squareAt(2,5);
        Square s41 = board.squareAt(4,1);
        Square s45 = board.squareAt(4,5);

        //creating some obstacles
        SquareTest.setSquareBuildLevel(s21,2);
        s43.addDome();
        Builder b45 = new Builder(s45, p1);
        Builder b41 = new Builder(s41, p2);
        Builder b25 = new Builder(s25, p3);

        //placing some builder
        Builder b23 = new Builder(s23, p1);                // b33 is the builder that is going to move
        Builder b12 = new Builder(s12, p2);
        Builder b13 = new Builder(s13, p2);
        Builder b14 = new Builder(s14, p2);
        Builder b24 = new Builder(s24, p2);
        Builder b34 = new Builder(s34, p2);
        Builder b33 = new Builder(s33, p2);
        Builder b32 = new Builder(s32, p2);
        Builder b22 = new Builder(s22, p2);

        List<Square> expectedList = null;               //Minotaur cannot move in any way
        Assert.assertSame(b23.getWalkableNeighborhood(),expectedList);

    }


}