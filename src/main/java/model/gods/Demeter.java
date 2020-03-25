package model.gods;

import model.Square;
import model.buildbehaviors.DoubleNotSameBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

import java.util.List;

public class Demeter extends God {

    public Demeter() {
        super("Demeter", new StandardWinCondition(), new NoStartTurn(), new StandardMove(), new DoubleNotSameBuild());
    }
}
