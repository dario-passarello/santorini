package model.gods;

import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.movebehaviors.SwapWithOpponentMove;
import model.wincondition.StandardWinCondition;

/**
 * The default {@link God} that has standard behaviors
 */
public class Mortal extends God {

    /**
     * Creates a Mortal's instance
     */
    public Mortal(){
        super("Mortal",
                new StandardWinCondition(),
                new StandardMove(),
                new StandardBuild(),
                false,
                false);
    }

}
