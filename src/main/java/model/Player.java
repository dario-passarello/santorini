package model;

import model.gods.God;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private Game game;
    private String name;
    private God god;
    private List<Builder> builders;

    public Player(Game game, String name) {
        this.game = game;
        this.name = name;
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
        return builders.stream().allMatch(builder -> builder.getBuildableNeighborhood().isEmpty());
    }

    public boolean checkBuildingLoseCondition() {
        return builders.stream().allMatch(builder -> builder.getBuildableNeighborhood().isEmpty());
    }


    /**
     * @return get a copy of the list of the builders controlled by the players
     */
    public List<Builder> getBuilders() {
        return new ArrayList<>(builders);
    }

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