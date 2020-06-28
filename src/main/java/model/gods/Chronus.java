package model.gods;

import model.Player;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.movebehaviors.SwapWithOpponentMove;
import model.wincondition.FiveTowerWinCondition;
import model.wincondition.StandardWinCondition;

import java.util.List;

/**
 * A {@link God} that has a {@link FiveTowerWinCondition} behavior
 */
public class Chronus extends God {

    /**
     * Creates a Chronus' instance
     */
    public Chronus(){
        super("Chronus",
                new StandardWinCondition(),
                new StandardMove(),
                new StandardBuild(),
                false,
                false);
        super.setWinCondition(new FiveTowerWinCondition(new StandardWinCondition(), this));
    }


    @Override
    public void configureAllOtherWinConditions(List<God> targets) {
        for(God g : targets) {
            g.setWinCondition(new FiveTowerWinCondition(g.getWinCondition(),this));
        }
    }
}
