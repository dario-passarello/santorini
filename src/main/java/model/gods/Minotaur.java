package model.gods;

import model.Square;
import model.buildbehaviors.StandardBuild;
import model.movebehaviors.OpponentPushMove;
import model.startbehaviors.NoStartTurn;
import model.wincondition.StandardWinCondition;

import java.util.List;

public class Minotaur extends God {

    public Minotaur() {
        God("Minotaur", new StandardWinCondition(), new NoStartTurn(), new OpponentPushMove(), new StandardBuild());
    }
}
