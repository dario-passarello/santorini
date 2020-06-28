package model.gods;

import model.Player;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.OpponentPushMove;
import model.movebehaviors.StandardMove;
import model.movebehaviors.SwapWithOpponentMove;
import model.wincondition.StandardWinCondition;

/**
 * A {@link God} that has a {@link OpponentPushMove} behavior
 */
public class Minotaur extends God {

    /**
     * Creates a Minotaur's instance
     */
    public Minotaur() {
        super("Minotaur",
                new StandardWinCondition(),
                new OpponentPushMove(new StandardMove()),
                new StandardBuild(),
                false,
                false);
    }

}
