package model;

import utils.Coordinate;

import java.util.*;

public class Square {
    final static int MAX_HEIGHT = 3;

    private Board board;
    private Coordinate coordinate;
    private boolean domed;
    private Integer buildLevel;
    private Builder occupant;


    public Square(Board board, Coordinate coordinate) {
        this.board = board;
        this.coordinate = coordinate;
        this.domed = false;
        this.buildLevel = 0;
        this.occupant = null;

    }

    /**
     * @return a reference to the board in which the square resides
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return A Coordinate object representing the location of this Square in the board
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * @return the squares adjacent to this square (also diagonal neighbors)
     */
    public Set<Square> getNeighbors() {
        HashSet<Square> sqSet = new HashSet<>();
        for(int x = Math.max(0,coordinate.getX() - 1); x <= Math.min(Board.BOARD_SIZE - 1, coordinate.getX() + 1); x++){
            for(int y = Math.max(0,coordinate.getY() - 1); y <= Math.min(Board.BOARD_SIZE - 1, coordinate.getY() + 1); y++) {
                if(x == coordinate.getX() && y == coordinate.getY()) continue;
                sqSet.add(board.squareAt(x,y));
            }
        }
        return sqSet;
    }

    /**
     * @return an optional object that could contain the builder on the square
     */
    public Optional<Builder> getOccupant(){
        return Optional.ofNullable(occupant);
    }
    /**
     * Sets the occupant of the square
     * @param builder the new occupant of the square
     */
    public void setOccupant(Builder builder) {
        occupant = builder;
    }

    /**
     *  Empties the square.  (no builder on it)
     */
    public void setEmptySquare() {
        occupant = null;
    }

    /**
     * Adds a build level. If applied when the build level is MAX_HEIGHT a dome will
     * automatically be built
     */
    public void build() {
        if(buildLevel < MAX_HEIGHT)
            buildLevel++;
        else
            domed = true;
    }
    /**
     * @return get the current build level (dome excluded)
     */
    public int getBuildLevel() {
        return buildLevel;
    }


    /**
     * @return true if a dome is built in the Square
     */
    public boolean isDomed() {
        return domed;
    }
    /**
     * Adds a dome over the tile
     */
    public void addDome() {
        domed = true;
    }
    /**
     * @return true if the square is not domed or built at the max level
     */
    public boolean isBuildable() {
        return !domed && buildLevel <= MAX_HEIGHT;
    }

    public boolean isPerimetral() {
        int x = coordinate.getX();
        int y = coordinate.getY();
        return x == 0 || x == Board.BOARD_SIZE - 1 || y == 0 || y == Board.BOARD_SIZE - 1;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Square)) return false;
        Square square = (Square) o;
        return board.equals(square.board) &&
                coordinate.equals(square.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, coordinate);
    }
}
