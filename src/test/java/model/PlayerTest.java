package model;

import model.gods.Apollo;
import model.gods.God;
import model.gods.Mortal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Coordinate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player;
    private Game game;
    private Board board;
    private God god;

    @Before
    public void setupTests() {
        List<String> names = Arrays.asList("test", "test2");
        try {
            game = new Game(names, 2);
        } catch (DuplicateNameException e) {
            System.err.println(e.getMessage());
        }
        board = game.getBoard();
        player = game.getPlayers().get(0);
        god = new Mortal();
        player.setGod(god);
        god.setPlayer(player);

    }
    @Test
    public void basicPlayerStuffShouldWork() {
        assertEquals("test",player.getName());
        assertSame(game,player.getGame());
        assertSame(god, player.getGod());
        assertFalse(player.isSpectator());

        Player copy = new Player(game,"test");
        assertEquals(copy, player);

        assertEquals(player.getBuilders().size(),0);
        //player.setGod(new Apollo());
        player.addBuilder(new Square(game.getBoard(),new Coordinate(0,0)));
        player.addBuilder(new Square(game.getBoard(),new Coordinate(4,4)));
        Assert.assertEquals(player.getBuilders().size(),2);

        assertFalse(player.checkMovingLoseCondition());
        assertFalse(player.checkBuildingLoseCondition());

        //all builders of player get stuck
        SquareTest.setSquareBuildLevel(board.squareAt(0,1), 4);
        SquareTest.setSquareBuildLevel(board.squareAt(1,0), 4);
        SquareTest.setSquareBuildLevel(board.squareAt(1,1), 4);
        SquareTest.setSquareBuildLevel(board.squareAt(3,4), 4);
        SquareTest.setSquareBuildLevel(board.squareAt(3,3), 4);
        SquareTest.setSquareBuildLevel(board.squareAt(4,3), 3);

        assertTrue(player.checkMovingLoseCondition());
        assertFalse(player.checkBuildingLoseCondition());

        SquareTest.setSquareBuildLevel(board.squareAt(4,3), 1);
        assertTrue(player.checkBuildingLoseCondition());

        player.setAsSpectator();
        assertTrue(player.isSpectator());
        assertFalse(board.squareAt(0,0).getOccupant().isPresent());
        assertFalse(board.squareAt(4,4).getOccupant().isPresent());


    }
}
