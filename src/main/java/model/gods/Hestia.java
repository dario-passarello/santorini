package model.gods;

import model.Player;
import model.buildbehaviours.DoubleNoPerimeterBuild;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

/**
 * Allows the player to build one additional time, but not on a perimetral space
 */
public class Hestia extends God {

    public Hestia(){
        super("Hestia", new StandardWinCondition(), new NoStartTurn(), new StandardMove(), new DoubleNoPerimeterBuild(new StandardBuild()));
    }

}
