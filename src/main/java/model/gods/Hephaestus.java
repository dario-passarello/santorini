package model.gods;

import model.buildbehaviours.DoubleSameBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

/**
 * Allows the player to build one additional block (not a dome) on top of the first block
 */
public class Hephaestus extends God {

    public Hephaestus(){
        super("Hephaestus", new StandardWinCondition(), new NoStartTurn(), new StandardMove(), new DoubleSameBuild());
    }

}
