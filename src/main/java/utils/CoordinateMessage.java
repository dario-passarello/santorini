package utils;

import model.Builder;

import java.io.Serializable;

public class CoordinateMessage implements Serializable {

    private Builder builder;
    private Coordinate coordinate;
    private boolean specialPower;

    public CoordinateMessage(Builder builder, Coordinate coordinate, boolean specialPower) {
        this.builder = builder;
        this.coordinate = coordinate;
        this.specialPower = specialPower;
    }

    public CoordinateMessage(Coordinate coordinate, boolean specialPower){
        this.builder = null;
        this.coordinate = coordinate;
        this.specialPower = specialPower;
    }

    public CoordinateMessage(Coordinate coordinate){
        this.builder = null;
        this.coordinate = coordinate;
        this.specialPower = false;
    }

    public Builder getBuilder(){
        return this.builder;
    }

    public Coordinate getCoordinate(){
        return this.coordinate;
    }

    public boolean getSpecialPower(){
        return this.specialPower;
    }
}
