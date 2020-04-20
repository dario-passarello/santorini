package model.gods;

import model.Player;
import model.buildbehaviours.AnyDomeBuild;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.wincondition.StandardWinCondition;

/**
 * Allows the player to build a dome at any level
 */
public class Atlas extends God {


    public Atlas(){
        super("Atlas",
                new StandardWinCondition(),
                new StandardMove(),
                new AnyDomeBuild(new StandardBuild()),
                false,
                true);
    }


    @Override
    public boolean hasSpecialBuildPower() {
        return true;
    }
}
