package model.gods;

import model.Player;
import model.buildbehaviours.BeneathBuild;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.movebehaviors.SwapWithOpponentMove;
import model.wincondition.StandardWinCondition;

/**
 * A {@link God} that has a {@link BeneathBuild} behavior
 */
public class Zeus extends God {

    /**
     * Creates a Zeus' instance
     */
    public Zeus(){
        super("Zeus",
                new StandardWinCondition(),
                new StandardMove(),
                new BeneathBuild(new StandardBuild()),
                false,
                false);
    }

}
