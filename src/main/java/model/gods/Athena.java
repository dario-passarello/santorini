package model.gods;

import model.buildbehaviours.StandardBuild;
import model.movebehaviors.BlockUpMove;
import model.movebehaviors.NoUpMove;
import model.movebehaviors.StandardMove;
import model.movebehaviors.SwapWithOpponentMove;
import model.wincondition.StandardWinCondition;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A {@link God} that has a {@link BlockUpMove} behavior
 */
public class Athena extends God {

    /**
     * Creates an Athena's instance
     */
    public Athena(){
        super( "Athena",
                new StandardWinCondition(),
                new BlockUpMove(new StandardMove()),
                new StandardBuild(),
                false,
                false);
        //INFLUENZA GLI AVVERSARI
    }

    /**
     * @param targets a list of gods whose move behaviors have to be changed
     */
    @Override
    public void setAllMoveBehaviors(List<God> targets) {
        //Apply temporary debuff to other players
        for(God g : targets.stream().filter(g -> !g.equals(this)).collect(Collectors.toList())) {
            g.setMoveBehavior(new NoUpMove(g.getMoveBehavior()));
        }
    }
}
