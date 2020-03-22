package model.gods;

import model.Square;
import model.buildbehaviors.StandardBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.TwoDownWinCondition;

import java.util.List;

public class Pan extends God {

    public Pan(){
        God("Pan", new TwoDownWinCondition(), new NoStartTurn(), new StandardMove(), new StandardBuild());
    }

}
