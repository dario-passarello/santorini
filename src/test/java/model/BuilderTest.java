package model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import utils.Coordinate;

import java.util.List;
import static org.junit.Assert.*;

public class BuilderTest
{
    @Test
    public void basicStuffShouldWork(){
        Game game = new Game(List.of("Player_1","Player_2"), 2);
        Board board = game.getBoard();
        Builder builder = new Builder(new Square(board, new Coordinate(1,2)),game.getPlayers().get(0),1);
        Builder copy = new Builder(new Square(board, new Coordinate(1,2)),game.getPlayers().get(0),1);
        assertEquals(builder, copy);
        assertNotSame(builder, copy);

        Builder fake = new Builder(new Square(board, new Coordinate(1,3)),game.getPlayers().get(0),2);
        assertNotEquals(builder, fake);
        assertEquals(builder.getOwner(), game.getPlayers().get(0));
    }
}
