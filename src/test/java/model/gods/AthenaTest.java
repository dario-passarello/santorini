package model.gods;

import model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class AthenaTest {



    private Player p1, p2, p3;
    private God g1, g2;
    private Board board;
    private Game g;
    private Square[][] s;

    @Before
    public void setUpTest(){
        g = new Game();
        board = g.getBoard();
        s = BoardTest.boardToMatrix(board);
        p1 = new Player(g,"player1");
        p2 = new Player(g,"player2");
        p3 = new Player(g,"player3");
        g1 = new Athena();
        g2 = new Atlas();
        p1.setGod(g1);
        p2.setGod(g2);
    }

    @Test
    public void enemyShouldNotMoveUp(){

    }

}