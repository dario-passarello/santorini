package model.gods;

import model.Player;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.movebehaviors.SwapWithOpponentMove;
import model.wincondition.NoPerimeterWinCondition;
import model.wincondition.StandardWinCondition;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A {@link God} that impose a {@link NoPerimeterWinCondition} behavior to other gods
 */
public class Hera extends God {

    /**
     * Creates a Hera's instance
     */
    public Hera(){
        super("Hera",
                new StandardWinCondition(),
                new StandardMove(),
                new StandardBuild(),
                false,
                false);
    }

    @Override
    public void configureAllOtherWinConditions(List<God> targets) {
        //Set No Perimeter win condition to all other gods
        for(God g : targets.stream().filter(g -> !g.equals(this)).collect(Collectors.toList())){
            g.setWinCondition(new NoPerimeterWinCondition(g.getWinCondition()));
        }
    }
}
