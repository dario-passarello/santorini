package model.gods;

import model.Player;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

public class Hera extends God {

    public Hera(){
        super("Hera", new StandardWinCondition(), new NoStartTurn(), new StandardMove(), new StandardBuild());
        //INFLUENZA I NEMICI
    }

    @Override
    public void resetBehaviors() {
    }

}
