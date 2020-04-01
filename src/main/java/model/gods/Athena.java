package model.gods;

import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

public class Athena extends God {

    public Athena(){
        super("Athena", new StandardWinCondition(), new NoStartTurn(), new StandardMove(), new StandardBuild());
        //INFLUENZA GLI AVVERSARI
    }

}
