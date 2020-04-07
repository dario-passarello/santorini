package model.gods;

import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

public class Mortal extends God {
    public Mortal(){
        super("Mortal",new StandardWinCondition(),new NoStartTurn(), new StandardMove(), new StandardBuild());
    }

    @Override
    public boolean hasSpecialStartPower() {
        return false;
    }
}
