package model;

import model.gods.God;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Player implements Serializable {

    public static final int BUILDERS_PER_PLAYER = 2;

    private transient final Game game;
    private final String name;
    private God god;
    private transient final List<Builder> builders;
    private Outcome status;

    public Player(Game game, String name) {
        this.game = game;
        this.name = name;
        this.status = Outcome.IN_GAME;
        this.builders = new ArrayList<>();
    }

    public Player(Player player) {
        this.game = null;
        this.builders = player.builders.stream().map(Builder::new).collect(Collectors.toList());
        this.name = player.name;
        if(player.god != null) {
            this.god = new God(player.god);
        }
        this.status = player.status;
    }

    /**
     * @param god Set god for the player
     */
    public void setGod(God god) {
        this.god = god;
    }

    /**
     * Places a new builder in the board assigned to the player
     * @param square The square where the new builder should be placed
     * @return the builder object placed in the board
     */
    public Builder addBuilder(Square square) {
        Builder b = new Builder(square, this, builders.size());
        builders.add(b);
        return b;
    }

    public void setStatus(Outcome status) {
        this.status = status;
        //if(!status.isActive()){
            //builders.forEach(Builder::removeBuilder);
        //}
    }

    /**
     * @return the god object related to the player
     */
    public God getGod(){
        return god;
    }
    public Game getGame() {
        return game;
    }
    public String getName() {
        return name;
    }

    public boolean checkMovingLoseCondition() {
        return builders.stream().allMatch(builder -> builder.getWalkableNeighborhood().isEmpty());
    }

    public boolean checkBuildingLoseCondition() {
        return builders.stream().allMatch(builder -> builder.getBuildableNeighborhood().isEmpty());
    }

    public Outcome getStatus() {
        return status;
    }

    /**
     * @return get a copyBehavior of the list of the builders controlled by the players
     */
    public List<Builder> getBuilders() {
        return new ArrayList<>(builders);
    }

    /**
     * Two players objects are equal if and only if they have the same name
     * @param o The other player object to compare
     * @return true if this and o are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}