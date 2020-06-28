package model.gods;

import model.Player;
import model.buildbehaviours.AnyDomeBuild;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.movebehaviors.SwapWithOpponentMove;
import model.wincondition.StandardWinCondition;

/**
 * A {@link God} that has a {@link AnyDomeBuild} behavior
 */
public class Atlas extends God {


    /**
     * Creates an Atlas' instance
     */
    public Atlas(){
        super("Atlas",
                new StandardWinCondition(),
                new StandardMove(),
                new AnyDomeBuild(new StandardBuild()),
                false,
                true);
    }

    @Override
    public boolean hasSpecialBuildPower() {
        return true;
    }
}
