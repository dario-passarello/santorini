package model;

import model.buildbehaviors.BuildBehavior;
import model.gods.God;

import java.util.List;

public class Player {
    private Game game;
    private String name;
    private God god;
    private List<Builder> builders;
    private boolean ready; //For the lobby

    public Player(Game game, String name) {
        this.game = game;
        this.name = name;
        ready = false;
    }

    /**
     * Toggles the ready status for the player waiting in lobby
     */
    public void toggleReady() {
        ready = !ready;
    }

    /**
     * @return true if the player is ready to start the game
     */
    public boolean isReady() {
        return ready;
    }

    /**
     * @param god Set god for the player
     */
    public void setGod(God god) {

    }

    /**
     * Places a new builder in the board assigned to the player
     * @param square The square where the new builder should be placed
     * @return the builder object placed in the board
     */
    public Builder buildBuilder(Square square) {
        //Add another builder
        return null; //TODO
    }

    /**
     * @return the god object related to the player
     */
    public God getGod(){
        return null; //TODO
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
        return null; //TODO (non una ref a builders)
    }




}
