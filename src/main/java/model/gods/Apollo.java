package model.gods;

import model.Player;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.movebehaviors.SwapWithOpponentMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;


public class Apollo extends God {
    
    public Apollo() {
        super("Apollo", new StandardWinCondition(), new NoStartTurn(), new SwapWithOpponentMove(new StandardMove()), new StandardBuild());

    }

}
