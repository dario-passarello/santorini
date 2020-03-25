package model.gods;

import model.Board;
import model.Builder;
import model.Player;
import model.Square;
import model.buildbehaviors.BuildBehavior;
import org.junit.Test;
import org.junit.Assert;

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
        Builder b1 = new Builder(s1,p1);
        Builder b2 = new Builder(s2,p2);
        b1.move(board.squareAt(2,2));
        Assert.assertEquals(s1.getOccupant(),b2);
        Assert.assertEquals(s2.getOccupant(),b1);
    }
}