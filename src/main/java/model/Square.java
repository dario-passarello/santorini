package model;

import utils.Coordinate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        this.buildLevel = 0;
        this.domed = false;
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
    public List<Square> getNeighborhood() {
        //TOdo
        return null;
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
        //TODO
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
        //TODO
        return false;
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
