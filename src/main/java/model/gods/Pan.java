package model.gods;

import model.Square;
import model.buildbehaviors.StandardBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;
import model.wincondition.TwoDownWinCondition;

import java.util.List;

public class Pan extends God {

    public Pan(){
        super("Pan", new TwoDownWinCondition(new StandardWinCondition()), new NoStartTurn(), new StandardMove(), new StandardBuild());
    }

}
