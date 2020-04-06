package model.gods;

import model.Player;
import model.buildbehaviours.DoubleNotSameBuild;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

/**
 * Allow the player to build one additional time, but not on the same space
 */
public class Demeter extends God {

    public Demeter() {
        super("Demeter", new StandardWinCondition(), new NoStartTurn(), new StandardMove(), new DoubleNotSameBuild(new StandardBuild()));
    }

    @Override
    public void resetBehaviors() {
    }
}
