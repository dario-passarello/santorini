package model;


import org.junit.Before;
import org.junit.Test;
import utils.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoardTest {
    private Board b;
    @Before
    public void setup() {
        b = new Board();
    }


    @Test
    public void boardAndSquaresShouldBeBuiltCorrectly() {

        for(int i = 0; i < Board.BOARD_SIZE; i++) {
            for(int j = 0; j < Board.BOARD_SIZE; j++) {
                assertEquals(b.squareAt(i,j).getCoordinate(),new Coordinate(i,j));
                assertSame(b.squareAt(i, j).getBoard(), b);
                assertEquals(b.squareAt(i,j).getBuildLevel(),0);
                assertFalse(b.squareAt(i, j).isDomed());
                assertFalse(b.squareAt(i,j).getOccupant().isPresent());
            }
        }
    }
    @Test
    public void squareAtTestCoordinatesShouldMatch() {
        assertEquals(b.squareAt(2,3).getCoordinate(), new Coordinate(2,3));
        assertEquals(b.squareAt(0,0).getCoordinate(), new Coordinate(0,0));
        assertEquals(b.squareAt(4,2).getCoordinate(), new Coordinate(4,2));
        assertThrows(IndexOutOfBoundsException.class, () -> b.squareAt(Board.BOARD_SIZE,2));
        assertThrows(IndexOutOfBoundsException.class, () -> b.squareAt(0,Board.BOARD_SIZE));

    }
    @Test
    public void getFreeSquareShouldWork() {
        List<Coordinate> free = new ArrayList<>();
        Random rand = new Random();
        for(int i = 0; i < Board.BOARD_SIZE; i++) {
            for(int j = 0; j < Board.BOARD_SIZE; j++) {
                free.add(new Coordinate(i,j));
            }
        }
        for(int i = 0; i < Board.BOARD_SIZE*Board.BOARD_SIZE; i++) {
            int toRemove = rand.nextInt(free.size());
            Coordinate extracted = free.get(toRemove);
            free.remove(toRemove);
            b.squareAt(extracted).setOccupant(new Builder(b.squareAt(extracted),null,i));
            assertEquals("Unexpected Free Size",
                    Board.BOARD_SIZE*Board.BOARD_SIZE - i - 1, b.getFreeSquares().size());
            assertTrue("Free square result is wrong", b.getFreeSquares().stream()
                .allMatch(s -> free.contains(s.getCoordinate())));
        }
    }
    public static Square[][] boardToMatrix(Board board){
        Square[][] s = new Square[Board.BOARD_SIZE][Board.BOARD_SIZE];
        for(int i = 0; i < Board.BOARD_SIZE; i++){              //row
            for(int j = 0; j < Board.BOARD_SIZE; j++) {         //column
                s[i][j] = board.squareAt(i,j);
            }
        }

        return s;
    }
}
