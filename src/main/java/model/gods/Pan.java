package model.gods;

import model.Player;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;
import model.wincondition.TwoDownWinCondition;

public class Pan extends God {

    public Pan(){
        super("Pan",
                new TwoDownWinCondition(new StandardWinCondition()),
                new NoStartTurn(),
                new StandardMove(),
                new StandardBuild(),
                false,
                false);
    }

}
