package model.gods;

import model.Player;
import model.buildbehaviours.BeneathBuild;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.wincondition.StandardWinCondition;

/**
 * Allows the player to build a block beneath itself
 */
public class Zeus extends God {

    public Zeus(){
        super("Zeus",
                new StandardWinCondition(),
                new StandardMove(),
                new BeneathBuild(new StandardBuild()),
                false,
                false);
    }

}
