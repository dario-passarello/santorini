package model;

import java.util.Optional;

public class Square {
    final static int MAX_HEIGHT = 3;

    private Board board;
    private Coordinate coordinate;
    private boolean domed;
    private Integer buildLevel;
    private Optional<Builder> occupant;

    public Square(Coordinate coordinate) {

    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setOccupant(Builder builder) {
        occupant = Optional.of(builder);
    }

    public void setEmptySquare() {
        occupant = Optional.empty();
    }

    public Optional<Builder> getOccupant(){
        return occupant; //TODO check if returns rep
    }

    public void build() {
        //TODO
    }

    public Integer getBuildLevel() {
        return buildLevel;
    }

    public boolean isDomed() {
        return domed;
    }

    public void addDome() {
        domed = true;
    }






}
