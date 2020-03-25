package model.gods;

import model.Square;
import model.buildbehaviors.StandardBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.FiveTowerWinCondition;
import model.wincondition.StandardWinCondition;

import java.util.List;

public class Chronus extends God {

    public Chronus(){
        super("Chronus", new FiveTowerWinCondition(new StandardWinCondition()), new NoStartTurn(), new StandardMove(), new StandardBuild());
    }

}
