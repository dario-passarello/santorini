package model;

import model.gods.GodFactory;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.Coordinate;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

        int hash = builder.hashCode();
        assertEquals(Objects.hash(builder.getOwner(), builder.getId()), hash);

        // Test Illegal States when God is NULL
        Assertions.assertThrows(IllegalStateException.class,
                ()-> builder.move(board.squareAt(0,0)));

        Assertions.assertThrows(IllegalStateException.class,
                ()-> builder.build(board.squareAt(0,0)));

        Assertions.assertThrows(IllegalStateException.class,
                ()-> builder.getBuildableNeighborhood());

        Assertions.assertThrows(IllegalStateException.class,
                ()-> builder.getWalkableNeighborhood());


        game.getFirstPlayer().setGod(new GodFactory().getGod("Mortal"));
        game.getFirstPlayer().getGod().setPlayer(game.getFirstPlayer());

        builder.getWalkableCoordinates();


    }


}
