package model.gods;

import model.Player;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.movebehaviors.SwapWithOpponentMove;
import model.wincondition.StandardWinCondition;

/**
 * A {@link God} that can build before the move phase
 * (in that case he will have a {@link model.movebehaviors.NoUpMove} behavior for the rest of the turn)
 */
public class Prometheus extends God {

    /**
     * Creates a Prometheus' instance
     */
    public Prometheus(){
        super("Prometheus",
                new StandardWinCondition(),
                new StandardMove(),
                new StandardBuild(),
                true,
                false);
    }

}
