package model.gods;

import model.Board;
import model.Game;
import model.Player;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArtemisTest {

    @Test
    public void test(){
        Game game = new Game(2);

        Player p1 = new Player(game,"player1");
        Player p2 = new Player(game,"player2");
        God g1 = new Artemis();
        God g2 = new Atlas();
        p1.setGod(g1);
        p2.setGod(g2);
        Board board = new Board();

        //TO BE CONTINUED
    }

}