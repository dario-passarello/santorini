package model.gods;

import model.Square;
import model.buildbehaviors.AnyDomeBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

import java.util.List;

public class Atlas extends God {

    public Atlas(){
        super("Atlas", new StandardWinCondition(), new NoStartTurn(), new StandardMove(), new AnyDomeBuild());
    }
}
