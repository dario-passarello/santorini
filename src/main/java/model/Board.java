package model;

import utils.Coordinate;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * a 5x5 board where most of the {@link Game} takes place
 */
public class Board implements Serializable {
    /**
     * Number of Squares per side of the board
     */
    public final static int BOARD_SIZE = 5;

    private transient Game game;
    private Square[][] matrix;

    /**
     * Board constructor (without a Game attribute)
     */
    public Board() {
        buildBoard();
    }

    /**
     *  Board constructor (with a Game attribute)
     */
    public Board(Game game) {
        this.game = game;
        buildBoard();
    }

    /**
     * Board deep-copyBehavior constructor.
     * Creates a new empty board with copying all squares states
     * @param b The board to be copied
     */
    public Board(Board b) {
        this.matrix = new Square[BOARD_SIZE][BOARD_SIZE];
        for(int x = 0; x < BOARD_SIZE; x++) {
            for(int y = 0; y < BOARD_SIZE; y++) {
                this.matrix[x][y] = b.matrix[x][y].copySquareStatus(this);
            }
        }
    }

    /**
     * Initializes an empty 5x5 board creating all Squares
     */
    private void buildBoard() {
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

    /**
     * @return the list of Coordinates without an occupant
     */
    public List<Coordinate> getFreeCoordinates() {
        return IntStream.range(0,BOARD_SIZE*BOARD_SIZE)
                .mapToObj(i -> matrix[i / BOARD_SIZE][i % BOARD_SIZE])
                .filter(sq -> !sq.getOccupant().isPresent())
                .map(Square::getCoordinate)
                .collect(Collectors.toList());
    }

    public void freeAllSquares() {
        IntStream.range(0,BOARD_SIZE*BOARD_SIZE)
                .mapToObj(i -> matrix[i / BOARD_SIZE][i % BOARD_SIZE])
                .forEach(Square::setEmptySquare);
    }

    /**
     * Check if a coordinate has components between 0 and BOARD_SIZE
     * @param coord A coordinate to check
     * @return true if the coordinate represents a square in the board
     */
    public static boolean checkValidCoordinate(Coordinate coord) {
        return coord.getX() >= 0
                && coord.getX() < BOARD_SIZE
                && coord.getY() >= 0
                && coord.getY() < BOARD_SIZE;
    }

}
