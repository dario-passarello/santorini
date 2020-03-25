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

        Square s1 = board.squareAt(3,3);
        Square s2 = board.squareAt(2,2);
        Square s3 = board.squareAt(2,3);
        Square s4 = board.squareAt(2,4);
        Square s5 = board.squareAt(3,4);
        Square s6 = board.squareAt(4,4);
        Square s7 = board.squareAt(4,3);
        Square s8 = board.squareAt(4,2);
        Square s9 = board.squareAt(3,2);
        //creating some buildings
        s1.setBuildLevel(0);
        s2.setBuildLevel(0);
        s3.setBuildLevel(1);
        s4.setBuildLevel(2);
        s5.setBuildLevel(3);
        s6.setBuildLevel(0);
        s7.setBuildLevel(1);
        s8.setBuildLevel(2);
        s9.setBuildLevel(3);
        //placing some builder
        Builder b33 = new Builder(s1,p1);                // b33 is the builder that is going to move
        Builder b22 = new Builder(s2,p2);
        Builder b23 = new Builder(s3, p2);
        Builder b24 = new Builder(s4, p2);
        Builder b34 = new Builder(s5, p2);
        Builder b44 = new Builder(s6, p1);
        Builder b43 = new Builder(s7, p1);
        Builder b42 = new Builder(s8, p1);
        Builder b32 = new Builder(s9, p1);

        List<Square> expectedList = (List<Square>) Arrays.asList(b22,b23);   //b1 should be able to move only on these squares
        Assert.assertEquals(expectedList, b33.getBuildableNeighborhood());
        
        b33.move(board.squareAt(2,2));
        Assert.assertEquals(s1.getOccupant(),b22);
        Assert.assertEquals(s2.getOccupant(),b33);
    }
}