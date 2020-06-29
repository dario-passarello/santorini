package model;

import model.gods.God;
import utils.Coordinate;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * a pawn of a {@link Player}
 */
public class Builder implements Serializable {

    private Square square;
    private final Player owner;
    private int id;

    /**
     * The constructor of the class
     * @param square The square where the builder will be placed
     * @param owner The Player who owns the builder
     * @param id The unique ID number of the created object
     */
    public Builder(Square square, Player owner, int id) {
        this.square = square;
        square.setOccupant(this);
        this.owner = owner;
        this.id = id;
    }

    /**
     * @param builder the Build that would be copied
     */
    public Builder(Builder builder) {
        this(new Square(builder.square), builder.getOwner(), builder.id);
    }

    /**
     * @return the reference of the square where the player is located
     */
    public Square getSquare() {
        return square; //TODO
    }

    /**
     * @param square the Square that the Builder occupy
     */
    public void setSquare(Square square) {
        this.square = square;
    }


    /**
     * @return the reference of the player who control this builder
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * @return id attribute
     */
    public int getId() {
        return id;
    }

    /**
     * @return a list containing the references to the squares where
     *         the builder from his position could be move according to the game rules
     *         and the controlling player's God powers
     */
    public List<Square> getWalkableNeighborhood() {
        God playerGod = owner.getGod();
        if(playerGod == null) {
            throw new IllegalStateException(ErrorMessage.GOD_NOT_ASSIGNED);
        }
        return playerGod.getWalkableNeighborhood(square);
    }

    /**
     * @return a list containing the references to the coordinates where
     *      the builder from his position could be move according to the game rules
     *      and the controlling player's God powers
     */
    public List<Coordinate> getWalkableCoordinates() {
        return getWalkableNeighborhood().stream()
                .map(Square::getCoordinate)
                .collect(Collectors.toList());
    }

    /**
     * @return a list containing the references to the squares where
     *         the builder could build from his position according to the game rules
     *         and the controlling player's God powers
     */
    public List<Square> getBuildableNeighborhood() {
        God playerGod = owner.getGod();
        if(playerGod == null) {
            throw new IllegalStateException(ErrorMessage.GOD_NOT_ASSIGNED);
        }
        return playerGod.getBuildableNeighborhood(square);
    }

    /**
     * @return a list containing the references to the coordinates where
     *      the builder could build from his position according to the game rules
     *      and the controlling player's God powers
     */
    public List<Coordinate> getBuildableCoordinates() {
        return getBuildableNeighborhood().stream()
                .map(Square::getCoordinate)
                .collect(Collectors.toList());
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
        if(playerGod == null) {
            throw new IllegalStateException(ErrorMessage.GOD_NOT_ASSIGNED);
        }
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
        if(playerGod == null) {
            throw new IllegalStateException(ErrorMessage.GOD_NOT_ASSIGNED);
        }
        return playerGod.build(this,sq);
    }

    /**
     * @param sq the Square where the Builder want to build a dome
     */
    public void buildDome(Square sq) {
        sq.addDome();
    }

    /**
     * removes the Builder from the Board
     */
    public void removeBuilder() {
        this.square.setEmptySquare();
        this.square = null;
    }

    /**
     * Two builder are equals if and only if their id is equal,
     * and their owner is equal
     * @param o The other object to compare
     * @return true if two builder are equal
     */
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
