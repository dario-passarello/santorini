package model.gods;

import model.Square;
import model.buildbehaviors.DoubleNoPerimeterBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

import java.util.List;

public class Hestia extends God {

    public Hestia(){
        God("Hestia", new StandardWinCondition(), new NoStartTurn(), new StandardMove(), new DoubleNoPerimeterBuild());
    }

}
