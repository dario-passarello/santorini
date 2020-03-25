package model.gods;

import model.Square;
import model.buildbehaviors.BeneathBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

import java.util.List;

public class Zeus extends God {

    public Zeus(){
        super("Zeus", new StandardWinCondition(), new NoStartTurn(), new StandardMove(), new BeneathBuild());
    }
}
