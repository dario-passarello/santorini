package model.gods;

import model.Square;
import model.buildbehaviors.StandardBuild;
import model.movebehaviors.SwapWithOpponentMove;
import model.startbehaviors.NoStartTurn;
import model.startbehaviors.StartBehavior;
import model.wincondition.StandardWinCondition;

import java.util.List;

public class Apollo extends God {

    public Apollo() {
        God("Apollo", new StandardWinCondition(), new NoStartTurn(), new SwapWithOpponentMove(), new StandardBuild());
    }

}
