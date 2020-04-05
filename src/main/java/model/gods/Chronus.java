package model.gods;

import model.Player;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.FiveTowerWinCondition;
import model.wincondition.StandardWinCondition;

public class Chronus extends God {

    public Chronus(Player player){
        super(player, "Chronus", new FiveTowerWinCondition(new StandardWinCondition(), player), new NoStartTurn(), new StandardMove(), new StandardBuild());
    }

    @Override
    public void resetBehaviors() {
    }
}
