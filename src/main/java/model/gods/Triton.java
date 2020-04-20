package model.gods;

import model.Player;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.StandardMove;
import model.movebehaviors.UnlimitedPerimetralMove;
import model.wincondition.StandardWinCondition;

public class Triton extends God{

    public Triton(){
        super("Triton",
                new StandardWinCondition(),
                new UnlimitedPerimetralMove(new StandardMove()), new StandardBuild(),
                false,
                false);
    }

}
