package model.gods;

import model.Player;
import model.buildbehaviours.AnyDomeBuild;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

/**
 * Allows the player to build a dome at any level
 */
public class Atlas extends God {


    public Atlas(Player player){
        super(player, "Atlas", new StandardWinCondition(), new NoStartTurn(), new StandardMove(), new AnyDomeBuild(new StandardBuild()));
    }

    @Override
    public void resetBehaviors() {
    }
}
