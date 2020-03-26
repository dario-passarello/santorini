package model.gods;

import model.Board;
import model.Player;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArtemisTest {

    @Test
    public void test(){
        Player p1 = new Player("player1");
        Player p2 = new Player("player2");
        God g1 = new Artemis();
        God g2 = new Atlas();
        p1.setGod(g1);
        p2.setGod(g2);
        Board board = new Board();

        //TO BE CONTINUED
    }

}