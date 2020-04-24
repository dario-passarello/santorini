package model.movebehaviors;

import model.*;
import model.gods.God;
import model.gods.Mortal;
import model.gods.Triton;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UnlimitedPerimetralMoveTest {


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
        g1 = new Triton();
        g2 = new Mortal();
        p1.setGod(g1);
        p2.setGod(g2);
        List<God> godList = Arrays.asList(g1, g2);
        g.setGodList(godList);
    }

    @Test
    public void youCanContinueAfterPerimeter(){

        Builder b13 = new Builder(board.squareAt(1,3), p1, 1);
        Assert.assertTrue(b13.move(board.squareAt(0,4)));           //from center to perimeter
        Assert.assertTrue(b13.move(board.squareAt(1,4)));           //from perimeter to perimeter
        Assert.assertFalse(b13.move(board.squareAt(1,3)));          //from perimeter to center
        Assert.assertFalse(b13.move(board.squareAt(2,3)));           //from center to center
    }

    @Test
    public void youShouldHaveSimpleNeighborhood(){
        Builder b04 = new Builder(s[0][4], p1, 1);
        Set<Square> expected = new HashSet<>(Arrays.asList(s[0][3], s[1][3], s[1][4]));
        Set<Square> actual = new HashSet<>(b04.getWalkableNeighborhood());
        Assert.assertEquals(expected, actual);
    }
}