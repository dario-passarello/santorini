package model.movebehaviors;

import model.*;
import model.gods.Atlas;
import model.gods.God;
import model.gods.Mortal;
import model.gods.Pan;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class StandardMoveTest {

    private Player p1, p2;
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
        g1 = new Mortal();
        g2 = new Pan();
        p1.setGod(g1);
        p2.setGod(g2);
        List<God> godList = Arrays.asList(g1, g2);
        g.setGodList(godList);
    }

    @Test
    public void simpleNeighborhoodTest(){
        Builder b22 = new Builder(s[2][2], p1, 1);     //full neighborhood
        Builder b14 = new Builder(s[1][4], p1, 2);     //edge neighborhood
        Builder b44 = new Builder(s[4][4], p1, 3);     //corner neighborhood

        Set<Square> expected = new HashSet<>(Arrays.asList(  s[1][1], s[1][2],s[1][3],
                                                    s[2][1], s[2][3],
                                                    s[3][1], s[3][2], s[3][3]));
        Set<Square> actual = new HashSet<>(b22.getWalkableNeighborhood());
        assertEquals(expected, actual);

        expected = new HashSet<>(Arrays.asList(s[0][3], s[0][4], s[1][3], s[2][3], s[2][4]));
        actual = new HashSet<>(b14.getWalkableNeighborhood());
        assertEquals(expected, actual);

        expected = new HashSet<>(Arrays.asList(s[3][3], s[3][4], s[4][3]));
        actual = new HashSet<>(b44. getWalkableNeighborhood());
        assertEquals(expected, actual);
    }

    @Test
    public void hinderedNeighborhoodTest(){
        Builder b22 = new Builder(s[2][2], p1, 1);
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
        Builder b42 = new Builder(s[2][1], p1, 2);
        Builder b32 = new Builder(s[2][3], p2, 1);

        Set<Square> expected = new HashSet<>(Arrays.asList(s[1][1]));
        Set<Square> actual = new HashSet<>(b22.getWalkableNeighborhood());
        assertTrue(expected.equals(actual));
    }

    @Test
    public void moveTest(){
        Builder b22 = new Builder(s[2][2], p1, 1);

        b22.move(s[3][3]);

        assertSame(s[3][3].getOccupant().orElse(null), b22);
        assertSame(s[2][2].getOccupant().orElse(null), null);

    }

}