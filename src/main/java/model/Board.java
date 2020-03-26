package model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    final static int BOARD_SIZE = 5;

    private List<List<Square>> matrix;
    //Pointer to game ??

    /**
     *  Initializes an empty 5x5 board creating all Square
     */
    public Board() {
        this.matrix = new ArrayList<>();
    }

    /**
     * @param row the number of the row
     * @param col the number of the column
     * @return the reference to the Square object located at (row,col)
     */
    public Square squareAt(int row, int col) throws IndexOutOfBoundsException {
        //TODO
        return null;
    }

    public List<List<Square>> getMatrix() {
        return matrix;
    }

    public void setMatrix(List<List<Square>> matrix) {
        this.matrix = matrix;
    }
}
