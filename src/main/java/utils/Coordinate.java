package utils;

import java.util.Objects;

public class Coordinate {
    //IMMUTABILE
    private final Integer x;
    private final Integer y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX(){
        return x;
    }

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
}
