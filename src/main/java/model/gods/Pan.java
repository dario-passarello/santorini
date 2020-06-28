package model.gods;

import model.Player;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.movebehaviors.SwapWithOpponentMove;
import model.wincondition.StandardWinCondition;
import model.wincondition.TwoDownWinCondition;

/**
 * A {@link God} that has a {@link TwoDownWinCondition} behavior
 */
public class Pan extends God {

    /**
     * Creates a Pan's instance
     */
    public Pan(){
        super("Pan",
                new TwoDownWinCondition(new StandardWinCondition()),
                new StandardMove(),
                new StandardBuild(),
                false,
                false);
    }

}
