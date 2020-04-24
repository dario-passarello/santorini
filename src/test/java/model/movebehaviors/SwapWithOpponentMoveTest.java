package model.movebehaviors;

import model.*;
import model.gods.Apollo;
import model.gods.God;
import model.gods.Mortal;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class SwapWithOpponentMoveTest {

    private Player p1, p2, p3;
    private God g1, g2;
    private Board board;
    private Game g;
    private Square[][] s;

    @BeforeEach
    public void init(){
        List<String> names = Arrays.asList("player1", "player2");
        try {
            g = new Game(names, 2);
        } catch (DuplicateNameException e) {
            System.err.println(e.getMessage());
        }
        board = g.getBoard();
        s = BoardTest.boardToMatrix(board);
        p1 = g.getPlayers().get(0);
        p2 = g.getPlayers().get(1);
        g1 = new Apollo();
        g2 = new Mortal();
        p1.setGod(g1);
        p2.setGod(g2);
        List<God> godList = Arrays.asList(g1, g2);
        g.setGodList(godList);
    }

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
        Builder b33 = new Builder(s[3][3], p1, 1);                // b33 is the builder that is going to move
        Builder b22 = new Builder(s[2][2], p2, 1);
        Builder b23 = new Builder(s[2][3], p2, 2);
        Builder b24 = new Builder(s[2][4], p2, 3);
        Builder b34 = new Builder(s[3][4], p2, 4);
        Builder b44 = new Builder(s[4][4], p1, 2);
        Builder b43 = new Builder(s[4][3], p1, 3);
        Builder b42 = new Builder(s[4][2], p1, 4);
        Builder b32 = new Builder(s[3][2], p1, 5);

        Set<Square> expected = new HashSet<>(Arrays.asList(s[2][2],s[2][3]));   //b1 should be able to move only on these squares
        Set<Square> actual = new HashSet<>(b33.getWalkableNeighborhood());
        Assert.assertEquals(expected, actual);

        b33.move(s[2][2]);

        Assert.assertSame(s[3][3].getOccupant().orElse(null),b22);
        Assert.assertSame(s[2][2].getOccupant().orElse(null),b33);

    }

}