package model.gods;

import model.Player;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.OpponentPushMove;
import model.movebehaviors.StandardMove;
import model.wincondition.StandardWinCondition;

public class Minotaur extends God {

    public Minotaur() {
        super("Minotaur",
                new StandardWinCondition(),
                new OpponentPushMove(new StandardMove()),
                new StandardBuild(),
                false,
                false);
    }

}
