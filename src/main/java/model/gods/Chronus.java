package model.gods;

import model.Player;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.FiveTowerWinCondition;
import model.wincondition.StandardWinCondition;

import java.util.List;

public class Chronus extends God {

    public Chronus(){
        super("Chronus", new StandardWinCondition(), new NoStartTurn(), new StandardMove(), new StandardBuild());
        super.setWinCondition(new FiveTowerWinCondition(new StandardWinCondition(), this));
    }

    @Override
    public void configureAllOtherWinConditions(List<God> targets) {
        for(God g : targets) {
            g.setWinCondition(new FiveTowerWinCondition(g.getWinCondition(),this));
        }
    }
}
