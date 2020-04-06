package model;

import model.gods.God;

import java.util.ArrayList;
import java.util.List;

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
        Builder b = new Builder(square, this);
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

    /**
     * @return get a copy of the list of the builders controlled by the players
     */
    public List<Builder> getBuilders() {
        return new ArrayList<>(builders);
    }

}