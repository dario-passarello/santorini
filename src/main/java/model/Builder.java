package model;

import java.util.List;

public class Builder {
    private Square position;
    private Player owner;

    public Builder(Square position, Player owner) {
        this.position = position;
        this.owner = owner;
    }

    public Square getPosition() {
        return position; //TODO
    }

    public Player getOwner() {
        return owner;
    }

    public List<Square> getWalkableNeighborhood() {
        return null; //TODO

    }

    public List<Square> getBuildableNeighborhood() {
        return null; //TODO
    }

    public void move(Square sq) {
        position = sq;
    }

    public void build(Square sq) {

    }

}
