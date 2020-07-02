package model.movebehaviors;

import model.*;
import model.buildbehaviours.BuildBehavior;
import model.buildbehaviours.DoubleSameBuild;
import model.gods.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlockUpMoveTest {

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
        g1 = new GodFactory().getGod("Athena");
        g2 = new Mortal();
        p1.setGod(g1);
        p2.setGod(g2);
        List<God> godList = Arrays.asList(g1, g2);
        g.setGodList(godList);
    }

    //here we test BlockUpMove and NoUpMove all at once
    @Test
    public void enemyShouldNotMoveUp(){
        Builder b01 = new Builder(s[0][1], p1, 1);
        Builder b44 = new Builder(s[4][4], p2, 1);

        SquareTest.setSquareBuildLevel(s[0][0], 1);
        SquareTest.setSquareBuildLevel(s[3][3], 1);

        b01.move(s[0][0]);
        Set<Square> expected = new HashSet<>(Arrays.asList(s[4][3], s[3][4]));
        Set<Square> actual = new HashSet<>(b44.getWalkableNeighborhood());

        Assert.assertEquals(expected, actual);

        //enemy will have a simple move
        b44.move(s[3][4]);
        Assert.assertEquals(s[4][4].getOccupant().orElse(null), null);
        Assert.assertEquals(s[3][4].getOccupant().orElse(null), b44);

    }

    @Test
    public void youShouldHaveSimpleNeighborhood(){
        Builder b01 = new Builder(s[0][1], p1, 1);
        //SquareTest.setSquareBuildLevel(s[0][0], 0);
        SquareTest.setSquareBuildLevel(s[1][0], 1);
        SquareTest.setSquareBuildLevel(s[1][1], 2);
        SquareTest.setSquareBuildLevel(s[1][2], 1);
        s[1][2].addDome();
        Builder b02 = new Builder(s[0][2], p1, 2);

        Set<Square> expected = new HashSet<>(Arrays.asList(s[0][0], s[1][0]));
        Set<Square> actual = new HashSet<>(b01.getWalkableNeighborhood());

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void copyTest(){
        MoveBehavior copy = g1.getMoveBehavior().copyBehavior();
        Assert.assertTrue(copy instanceof BlockUpMove);
        int hash = g1.hashCode();
        assertEquals(Objects.hash(g1.getPlayer(), g1.getName()), hash);
        copy = new NoUpMove(new StandardMove());
        copy.copyBehavior();
        Assert.assertTrue(copy instanceof NoUpMove);

    }
}