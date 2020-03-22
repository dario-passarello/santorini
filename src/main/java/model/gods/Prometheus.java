package model.gods;

import model.Square;
import model.buildbehaviors.StandardBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.BuildStartTurn;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

import java.util.List;

public class Prometheus extends God {

    public Prometheus(){
        God("Prometheus", new StandardWinCondition(), new BuildStartTurn(), new StandardMove(), new StandardBuild());
    }

}
