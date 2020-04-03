package model;

import utils.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public final static int BOARD_SIZE = 5;

    private Square[][] matrix;
    //Pointer to game ??

    /**
     *  Initializes an empty 5x5 board creating all Squares
     */
    public Board() {
        this.matrix = new Square[BOARD_SIZE][BOARD_SIZE];
        //TODO
    }

    /**
     * @param row the number of the row
     * @param col the number of the column
     * @return the reference to the Square object located at (row,col)
     */
    public Square squareAt(int row, int col) {
        //TODO
        return null;
    }
    /**
     * @param coord The coordinate object representing the coordinates of
     *              the requested Square object
     * @return the reference to the Square object located at the coordinate
     */
    public Square squareAt(Coordinate coord) {
        //TODO
        return null;
    }

    /**
     * @return squares in the board that aren't occupied by a builder
     */
    public List<Square> getFreeSquares() {
        //TODO
        return null;
    }

}
