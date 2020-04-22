package model;

import model.gods.Apollo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Coordinate;

import java.util.Arrays;
import java.util.List;

public class PlayerTest {

    private Player player;
    private Game game;

    @Before
    public void setupTests() {
        List<String> names = Arrays.asList("test", "test2");
        try {
            game = new Game(names, 2);
        } catch (DuplicateNameException e) {
            System.err.println(e.getMessage());
        }
        player = game.getPlayers().get(0);

    }
    @Test
    public void basicPlayerStuffShouldWork() {
        //Assert.assertSame(game,player.getGame());
        Assert.assertEquals("test",player.getName());
        Assert.assertEquals(player.getBuilders().size(),0);
        player.setGod(new Apollo());
        player.addBuilder(new Square(game.getBoard(),new Coordinate(2,1)));
        player.addBuilder(new Square(game.getBoard(),new Coordinate(4,1)));
        Assert.assertEquals(player.getBuilders().size(),2);
    }
}
