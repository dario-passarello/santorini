package model.gods;

import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.wincondition.StandardWinCondition;

public class Mortal extends God {
    public Mortal(){
        super("Mortal",
                new StandardWinCondition(),
                new StandardMove(),
                new StandardBuild(),
                false,
                false);
    }

}
