package model.gods;

import model.Player;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.DoubleNoBackMove;
import model.movebehaviors.StandardMove;
import model.movebehaviors.SwapWithOpponentMove;
import model.wincondition.StandardWinCondition;

/**
 * A {@link God} that has a {@link DoubleNoBackMove} behavior
 */
public class Artemis extends God {

    /**
     * Creates an Artemis' instance
     */
    public Artemis(){
        super("Artemis",
            new StandardWinCondition(),
            new DoubleNoBackMove(new StandardMove()), new StandardBuild(),
            false,
            false
        );
    }

}
