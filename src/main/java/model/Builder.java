package model;

import model.gods.God;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Builder implements Serializable {

    private Square position;
    private Player owner;
    private int id;

    public Builder(Square position, Player owner, int id) {
        this.position = position;
        position.setOccupant(this);
        this.owner = owner;
        this.id = id;
    }

    /**
     * @return the reference of the square where the player is located
     */
    public Square getPosition() {
        return position; //TODO
    }

    public void setPosition(Square position) {
        this.position = position;
    }


    /**
     * @return the reference of the player who control this builder
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * @return a list containing the references to the squares where
     *         the builder from his position could be move according to the game rules
     *         and the controlling player's God powers
     */
    public List<Square> getWalkableNeighborhood() {
        God playerGod = owner.getGod();
        return playerGod.getWalkableNeighborhood(position);
    }

    /**
     * @return a list containing the references to the squares where
     *         the builder could build from his position according to the game rules
     *         and the controlling player's God powers
     */
    public List<Square> getBuildableNeighborhood() {
        God playerGod = owner.getGod();
        return playerGod.getBuildableNeighborhood(position);
    }

    /**
     * Moves the player
     * @param sq The square where the builder will be moved
     * @return true if the builder could be moved again after this move
     *          because of his power, false if his move phase is terminated
     *          or the builder move was forced
     */
    public boolean move(Square sq) {
        God playerGod = owner.getGod();
        return playerGod.move(this,sq);
    }


    /**
     * Builds in the square passed as parameter
     * @param sq The square where the builder will build a structure
     * @return true if the builder could build again after this move
     *          because of his power, false if his build phase is terminated
     *
     */
    public boolean build(Square sq) {
        God playerGod = owner.getGod();
        return playerGod.build(this,sq);
    }

    public boolean buildDome(Square sq) {
        sq.addDome();
        return false;
    }

    public void removeBuilder() {
        this.position.setEmptySquare();
        this.position = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Builder)) return false;
        Builder builder = (Builder) o;
        return id == builder.id &&
                owner.equals(builder.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, id);
    }



}
