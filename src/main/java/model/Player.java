package model;

import model.buildbehaviors.BuildBehavior;
import model.gods.God;

import java.util.List;

public class Player {
    private String name;
    private God god;
    private List<Builder> builders;
    private boolean ready; //For the lobby

    public Player(String name) {
        this.name = name;
        ready = false;
    }


    public void toggleReady() {
        ready = !ready;
    }

    public boolean isReady() {
        return ready;
    }

    public void setGod(God god) {

    }

    public Builder buildBuilder(Square square) {
        //Add another builder
        return null; //TODO
    }

    public God getGod(){
        return null; //TODO
    }

    public List<Builder> getBuilders() {
        return null; //TODO (non una ref a builders)
    }




}
