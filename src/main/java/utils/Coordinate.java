package utils;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class is the representation of a standard two-dimensional coordinate. It is often used to locate objects in the board
 */
public class Coordinate implements Serializable {
    //IMMUTABILE
    private final Integer x;
    private final Integer y;

    /**
     * The constructor of the class
     * @param x The first coordinate (line)
     * @param y The second coordinate (column)
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The getter method for the first coordinate
     * @return The value of the first coordinate
     */
    public Integer getX(){
        return x;
    }

    /**
     * The getter method of the second coordinate
     * @return The value of the second coordinate
     */
    public Integer getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
