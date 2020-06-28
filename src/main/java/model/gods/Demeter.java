package model.gods;

import model.buildbehaviours.DoubleNotSameBuild;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.movebehaviors.SwapWithOpponentMove;
import model.wincondition.StandardWinCondition;

/**
 * A {@link God} that has a {@link DoubleNotSameBuild} behavior
 */
public class Demeter extends God {

    /**
     * Creates a Demeter's instance
     */
    public Demeter() {
        super("Demeter",
                new StandardWinCondition(),
                new StandardMove(),
                new DoubleNotSameBuild(new StandardBuild()),
                false,
                false);
    }

}
