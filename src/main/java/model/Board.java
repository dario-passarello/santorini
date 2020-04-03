package model;

import utils.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    final static int BOARD_SIZE = 5;

    private Square[][] matrix;

    /**
     *  Initializes an empty 5x5 board creating all Squares
     */
    public Board() {
        this.matrix = new Square[BOARD_SIZE][BOARD_SIZE];
        for(int x = 0; x < BOARD_SIZE; x++) {
            for(int y = 0; y < BOARD_SIZE; y++) {
                matrix[x][y] = new Square(this, new Coordinate(x,y));
            }
        }
    }

    /**
     * @param row the number of the row
     * @param col the number of the column
     * @return the reference to the Square object located at (row,col)
     */
    public Square squareAt(int row, int col) {
        return matrix[row][col];
    }
    /**
     * @param coord The coordinate object representing the coordinates of
     *              the requested Square object
     * @return the reference to the Square object located at the coordinate
     */
    public Square squareAt(Coordinate coord) {
        return matrix[coord.getX()][coord.getY()];
    }

    /**
     * @return squares in the board that aren't occupied by a builder
     */
    public List<Square> getFreeSquares() {
        return IntStream.range(0,BOARD_SIZE*BOARD_SIZE)
                .mapToObj(i -> matrix[i / BOARD_SIZE][i % BOARD_SIZE])
                .filter(sq -> !sq.getOccupant().isPresent())
                .collect(Collectors.toList());

    }

}
