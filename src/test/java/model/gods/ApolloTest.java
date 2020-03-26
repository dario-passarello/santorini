package model.gods;

import model.Board;
import model.Builder;
import model.Player;
import model.Square;
import model.buildbehaviors.BuildBehavior;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApolloTest {

    /**
     * this test checks if the Apollo's move behavior works
     */
    @Test
    public void test(){
        Player p1 = new Player("player1");
        Player p2 = new Player("player2");
        God g1 = new Apollo();
        God g2 = new Atlas();
        p1.setGod(g1);
        p2.setGod(g2);
        Board board = new Board();

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
        s33.setBuildLevel(0);
        s22.setBuildLevel(0);
        s23.setBuildLevel(1);
        s24.setBuildLevel(2);
        s34.setBuildLevel(3);
        s44.setBuildLevel(0);
        s43.setBuildLevel(1);
        s42.setBuildLevel(2);
        s32.setBuildLevel(3);
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

        b33.move(board.squareAt(2,2));
        Assert.assertEquals(s33.getOccupant(),b22);
        Assert.assertEquals(s22.getOccupant(),b33);
    }
}