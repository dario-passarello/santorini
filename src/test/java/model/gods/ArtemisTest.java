package model.gods;

import model.*;
import org.junit.Before;
import org.junit.Test;

class ArtemisTest {

    private Player p1, p2, p3;
    private God g1, g2;
    private Board board;
    private Game g;
    private Square[][] s;

    @Before
    public void init(){
        g = new Game();
        board = g.getBoard();
        s = BoardTest.boardToMatrix(board);
        Player p1 = new Player(g,"player1");
        Player p2 = new Player(g,"player2");
        God g1 = new Artemis();
        God g2 = new Atlas();
        p1.setGod(g1);
        p2.setGod(g2);
    }

    @Test
    public void youShouldNotComeBack(){
        //TODO
    }

}