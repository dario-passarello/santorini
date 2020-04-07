package model.gods;

import model.Player;
import model.buildbehaviours.StandardBuild;
import model.movebehaviors.OpponentPushMove;
import model.movebehaviors.StandardMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

public class Minotaur extends God {

    public Minotaur() {
        super("Minotaur", new StandardWinCondition(), new NoStartTurn(), new OpponentPushMove(new StandardMove()), new StandardBuild());
    }


}
