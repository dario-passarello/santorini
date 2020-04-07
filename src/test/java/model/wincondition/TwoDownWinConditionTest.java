package model.wincondition;

import model.*;
import model.gods.Atlas;
import model.gods.God;
import model.gods.Pan;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

class TwoDownWinConditionTest {

    private Player p1, p2;
    private God g1, g2;
    private Board board;
    private Builder b1;
    private Game g;
    private Square[][] s;

    @Before
    public void init() {
        g = new Game();
        board = g.getBoard();
        s = BoardTest.boardToMatrix(board);
        p1 = new Player(g, "player1");
        p2 = new Player(g, "player2");
        g1 = new Pan();                     //Pan have this win condition
        g2 = new Atlas();
        p1.setGod(g1);
        p2.setGod(g2);
    }

    @Test
    public void youShouldWin() {
        Player expectedWinner = p1;
        for(int i = 0; i < 2; i++) {                        //checking moves from lvl. 2 to 0 and 3 to 1, not checking 3 to 0
            SquareTest.setSquareBuildLevel(s[3][3], i);
            SquareTest.setSquareBuildLevel(s[3][2], i+2);
            Square start = s[3][2];
            b1 = p1.addBuilder(s[3][3]);
            Assert.assertSame(p1.getGod().getWinCondition().checkWinCondition(start, b1).orElse(null), expectedWinner);
        }
    }

    @Test
    public void youShouldNotWin() {
        Player expectedWinner = null;
        for(int i = 0; i < 3; i++) {                        //checking moves from lvl. 1 to 0, 2 to 1 and 3 to 2
            SquareTest.setSquareBuildLevel(s[3][3], i);
            SquareTest.setSquareBuildLevel(s[3][2], i+1);
            Square start = s[3][2];
            b1 = p1.addBuilder(s[3][3]);
            Assert.assertSame(p1.getGod().getWinCondition().checkWinCondition(start, b1).orElse(null), expectedWinner);
        }
    }
}