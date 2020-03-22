package model.gods;

import model.Square;
import model.buildbehaviors.DoubleSameBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

import java.util.List;

public class Hephaestus extends God {

    public Hephaestus(){
        God("Hephaestus", new StandardWinCondition(), new NoStartTurn(), new StandardMove(), new DoubleSameBuild());
    }

}
