package model.gods;

import model.Square;
import model.buildbehaviors.StandardBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

import java.util.List;

public class Hera extends God {

    public Hera(){
        God("Hera", new StandardWinCondition(), new NoStartTurn(), new StandardMove(), new StandardBuild());
        //INFLUENZA I NEMICI
    }

}
