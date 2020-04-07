package model.gods;

import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class MinotaurTest {

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
        g1 = new Minotaur();
        g2 = new Atlas();
        p1.setGod(g1);
        p2.setGod(g2);
    }

    @Test
    public void canUsePower(){
        Square[][] s = new Square[Board.BOARD_SIZE][Board.BOARD_SIZE];
        for(int i = 0; i < Board.BOARD_SIZE; i++){              //row
            for(int j = 0; j < Board.BOARD_SIZE; j++) {         //column
                s[i][j] = board.squareAt(i,j);
            }
        }

        //creating some buildings
        SquareTest.setSquareBuildLevel(s[0][0],1);
        SquareTest.setSquareBuildLevel(s[0][2],2);
        SquareTest.setSquareBuildLevel(s[0][4],3);
        SquareTest.setSquareBuildLevel(s[1][2],1);
        SquareTest.setSquareBuildLevel(s[1][3],2);
        SquareTest.setSquareBuildLevel(s[2][1],1);
        SquareTest.setSquareBuildLevel(s[2][3],3);
        SquareTest.setSquareBuildLevel(s[2][4],3);
        SquareTest.setSquareBuildLevel(s[3][1],2);
        SquareTest.setSquareBuildLevel(s[3][2],3);
        SquareTest.setSquareBuildLevel(s[3][3],3);
        SquareTest.setSquareBuildLevel(s[4][4],1);
        //placing some builder
        Builder b22 = new Builder(s[2][2], p1);                 //b22 is the builder that is going to move
        Builder b11 = new Builder(s[1][1], p2);
        Builder b12 = new Builder(s[1][2], p2);
        Builder b13 = new Builder(s[1][3], p2);
        Builder b21 = new Builder(s[2][1], p2);
        Builder b23 = new Builder(s[2][3], p2);
        Builder b31 = new Builder(s[3][1], p2);
        Builder b32 = new Builder(s[3][2], p2);
        Builder b33 = new Builder(s[3][3], p2);

        List<Square> expectedList = Arrays.asList(s[1][1], s[1][2], s[1][1]);
        Assert.assertEquals(expectedList, b22.getWalkableNeighborhood());

        SquareTest.setSquareBuildLevel(s[2][2],1);
        expectedList.addAll(Arrays.asList(s[1][3],s[3][1]));
        Assert.assertEquals(expectedList, b22.getWalkableNeighborhood());

        SquareTest.setSquareBuildLevel(s[2][2],2);
        expectedList.addAll(Arrays.asList(s[2][3], s[3][2], s[3][3]));
        Assert.assertEquals(expectedList, b22.getWalkableNeighborhood());

        SquareTest.setSquareBuildLevel(s[2][2],3);
        Assert.assertEquals(expectedList, b22.getWalkableNeighborhood());


        b22.move(s[3][3]);          //Minotaur push
        Assert.assertFalse(s[2][2].getOccupant().isPresent());
        Assert.assertSame(s[3][3].getOccupant().orElse(null), b22);
        Assert.assertSame(s[4][4].getOccupant().orElse(null), b33);

    }

    @Test
    public void canNotUsePower(){
        Square s01 = board.squareAt(0,1);
        Square s02 = board.squareAt(0,2);
        Square s03 = board.squareAt(0,3);
        Square s11 = board.squareAt(1,1);
        Square s12 = board.squareAt(1,2);
        Square s13 = board.squareAt(1,3);
        Square s21 = board.squareAt(2,1);
        Square s22 = board.squareAt(2,2);
        Square s23 = board.squareAt(2,3);
        Square s30 = board.squareAt(3,0);
        Square s14 = board.squareAt(1,4);
        Square s34 = board.squareAt(3,4);

        //creating some buildings
        SquareTest.setSquareBuildLevel(s13,1);
        SquareTest.setSquareBuildLevel(s14,3);
        SquareTest.setSquareBuildLevel(s22,2);
        SquareTest.setSquareBuildLevel(s23,1);
        s34.addDome();
        //placing some builder
        Builder b01 = new Builder(s01, p1);
        Builder b02 = new Builder(s02, p2);
        Builder b03 = new Builder(s03, p2);
        Builder b11 = new Builder(s11, p1);
        Builder b12 = new Builder(s12, p1);         // b12 is the builder that is going to move
        Builder b13 = new Builder(s13, p2);
        Builder b21 = new Builder(s21, p2);
        Builder b22 = new Builder(s22, p2);
        Builder b23 = new Builder(s23, p2);
        Builder b30 = new Builder(s30, p1);

        List<Square> expectedList = new ArrayList<>();              //b12 cannot move, so it's empty
        Assert.assertEquals(expectedList, b12.getWalkableNeighborhood());

    }



}