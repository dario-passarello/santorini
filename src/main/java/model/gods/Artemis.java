package model.gods;

import model.Player;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.DoubleNoBackMove;
import model.movebehaviors.StandardMove;
import model.wincondition.StandardWinCondition;

public class Artemis extends God {

    public Artemis(){
        super("Artemis",
            new StandardWinCondition(),
            new DoubleNoBackMove(new StandardMove()), new StandardBuild(),
            false,
            false
        );
    }

}
