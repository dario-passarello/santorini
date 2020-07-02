package model.wincondition;

import model.*;
import model.gods.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class NoPerimeterWinConditionTest {

    private Player p1, p2;
    private God g1, g2;
    private Board board;
    //private Builder b2;
    private Game g;
    private Square[][] s;

    @BeforeEach
    public void init()
    {
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
        g1 = new GodFactory().getGod("Hera");                     //she applies this malus to the win condition
        g2 = new Mortal();
        p1.setGod(g1);
        p2.setGod(g2);
        g1.setPlayer(p1);
        g2.setPlayer(p2);
        List<God> godList = Arrays.asList(g1, g2);
        g.setGodList(godList);
        g1.configureAllOtherWinConditions(g.getGodList());
    }

    @Test
    public void youShouldNotWinOnPerimeter(){
        SquareTest.setSquareBuildLevel(s[4][1],3);
        SquareTest.setSquareBuildLevel(s[3][2],2);
        Square start = s[3][2];
        Builder b41 = new Builder(s[4][1], p2, 1);
        Assert.assertSame(null, p2.getGod().getWinCondition().checkWinCondition(start,b41).orElse(null));

        SquareTest.setSquareBuildLevel(s[0][3],3);
        SquareTest.setSquareBuildLevel(s[0][2],2);
        start = s[0][2];
        Builder b03 = new Builder(s[0][3], p2, 2);
        Assert.assertSame(null, p2.getGod().getWinCondition().checkWinCondition(start,b03).orElse(null));

        SquareTest.setSquareBuildLevel(s[1][0],3);
        SquareTest.setSquareBuildLevel(s[0][1],2);
        start = s[0][1];
        Builder b10 = new Builder(s[1][0], p2, 2);
        Assert.assertSame(null, p2.getGod().getWinCondition().checkWinCondition(start,b10).orElse(null));

        SquareTest.setSquareBuildLevel(s[2][4],3);
        SquareTest.setSquareBuildLevel(s[3][3],2);
        start = s[3][3];
        Builder b24 = new Builder(s[2][4], p2, 2);
        Assert.assertSame(null, p2.getGod().getWinCondition().checkWinCondition(start,b24).orElse(null));

        SquareTest.setSquareBuildLevel(s[2][2],3);
        SquareTest.setSquareBuildLevel(s[2][1],2);
        start = s[2][1];
        Builder b22 = new Builder(s[2][2], p2, 2);
        Assert.assertSame(p2, p2.getGod().getWinCondition().checkWinCondition(start,b22).orElse(null));
    }

    @Test
    public void specialWinConditionCheck(){
        Assert.assertNull(p2.getGod().checkSpecialWinCondition().orElse(null));
    }

}