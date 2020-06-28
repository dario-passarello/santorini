package model.gods;

import model.Player;
import model.buildbehaviours.DoubleSameBuild;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.movebehaviors.SwapWithOpponentMove;
import model.wincondition.StandardWinCondition;

/**
 * A {@link God} that has a {@link DoubleSameBuild} behavior
 */
public class Hephaestus extends God {

    /**
     * Creates a Hephaestus' instance
     */
    public Hephaestus(){
        super("Hephaestus",
                new StandardWinCondition(),
                new StandardMove(),
                new DoubleSameBuild(new StandardBuild()),
                false,
                false);
    }

}
