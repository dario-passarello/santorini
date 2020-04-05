package model.gods;

import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.movebehaviors.UnlimitedPerimetralMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

public class Triton extends God{

    public Triton(){
        super("Triton", new StandardWinCondition(), new NoStartTurn(), new UnlimitedPerimetralMove(new StandardMove()), new StandardBuild());
    }

    @Override
    public void resetBehaviors() {
    }

}
