package model;

import model.gods.Apollo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Coordinate;

public class PlayerTest {

    private Player player;
    private Game game;

    @Before
    public void setupTests() {
        game = new Game();
        player = new Player(game,"test");

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
