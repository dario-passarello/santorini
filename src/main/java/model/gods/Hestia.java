package model.gods;

import model.Player;
import model.buildbehaviours.DoubleNoPerimeterBuild;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.movebehaviors.SwapWithOpponentMove;
import model.wincondition.StandardWinCondition;

/**
 * A {@link God} that has a {@link DoubleNoPerimeterBuild} behavior
 */
public class Hestia extends God {

    /**
     * Creates a Hestia's instance
     */
    public Hestia(){
        super("Hestia",
                new StandardWinCondition(),
                new StandardMove(),
                new DoubleNoPerimeterBuild(new StandardBuild()),
                false,
                false);
    }
}
