package model.gods;

import model.Player;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.wincondition.StandardWinCondition;

public class Prometheus extends God {

    public Prometheus(){
        super("Prometheus",
                new StandardWinCondition(),
                new StandardMove(),
                new StandardBuild(),
                true,
                false);
    }

}
