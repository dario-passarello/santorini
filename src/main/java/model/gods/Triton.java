package model.gods;

import model.Player;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.movebehaviors.SwapWithOpponentMove;
import model.movebehaviors.UnlimitedPerimetralMove;
import model.wincondition.StandardWinCondition;

/**
 * A {@link God} that has a {@link UnlimitedPerimetralMove} behavior
 */
public class Triton extends God{

    /**
     * Creates a Triton's instance
     */
    public Triton(){
        super("Triton",
                new StandardWinCondition(),
                new UnlimitedPerimetralMove(new StandardMove()), new StandardBuild(),
                false,
                false);
    }

}
