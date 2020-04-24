package model.wincondition;

import model.*;
import model.gods.Atlas;
import model.gods.God;
import model.gods.Pan;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.synth.SynthTabbedPaneUI;
import java.util.Arrays;
import java.util.List;

class TwoDownWinConditionTest {

    private Player p1, p2;
    private God g1, g2;
    private Board board;
    private Builder b1;
    private Game g;
    private Square[][] s;

    @BeforeEach
    public void init() {
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
        g1 = new Pan();                     //Pan have this win condition
        g2 = new Atlas();
        p1.setGod(g1);
        p2.setGod(g2);
        List<God> godList = Arrays.asList(g1, g2);
        g.setGodList(godList);
    }

    @Test
    public void youShouldWin() {
        Player expectedWinner = p1;
        for(int i = 0; i < 2; i++) {                        //checking moves from lvl. 2 to 0 and 3 to 1, not checking 3 to 0
            SquareTest.setSquareBuildLevel(s[3][3], i);
            SquareTest.setSquareBuildLevel(s[3][2], i+2);
            Square start = s[3][2];
            b1 = new Builder(s[3][3], p1, 1);
            Assert.assertSame(p1.getGod().getWinCondition().checkWinCondition(start, b1).orElse(null), expectedWinner);
        }
    }

    @Test
    public void youShouldNotWin() {
        Player expectedWinner = null;
        Square start = s[3][2];
        b1 = new Builder(s[3][3], p1, 1);

        //for(int i = 0; i < 3; i++) {                        //checking moves from lvl. 1 to 0, 2 to 1 and 3 to 2
        SquareTest.setSquareBuildLevel(s[3][2], 1);
        Player actualWinner = p1.getGod().getWinCondition().checkWinCondition(start, b1).orElse(null);
        Assert.assertSame(expectedWinner, actualWinner);
        for(int i = 0; i < 2; i++){
            SquareTest.setSquareBuildLevel(s[3][3], 1);
            SquareTest.setSquareBuildLevel(s[3][2], 1);
            actualWinner = p1.getGod().getWinCondition().checkWinCondition(start, b1).orElse(null);
            Assert.assertSame(expectedWinner, actualWinner);
        }
    }
}