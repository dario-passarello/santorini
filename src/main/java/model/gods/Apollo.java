package model.gods;

import model.Player;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.movebehaviors.SwapWithOpponentMove;
import model.wincondition.StandardWinCondition;


/**
 * A {@link God} that has a {@link SwapWithOpponentMove} behavior
 */
public class Apollo extends God {

    /**
     * Creates an Apollo's instance
     */
    public Apollo() {
        super("Apollo",
                new StandardWinCondition(),
                new SwapWithOpponentMove(new StandardMove()), new StandardBuild(),
                false,
                false
        );

    }
}
