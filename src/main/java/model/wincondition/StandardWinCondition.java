package model.wincondition;

import model.Builder;
import model.Player;
import model.Square;

import java.util.Optional;

//TODO
public class StandardWinCondition implements WinCondition{

    public StandardWinCondition(){}

    /**
     * @param start is the initial position of the current builder (before move)
     * @param builder is the current builder
     * @return true if the builder moved up, reaching a building at level 3
     */
    public Optional<Player> checkWinCondition(Square start, Builder builder){
        if(builder.getPosition().getBuildLevel() == 3 && start.getBuildLevel() == 2)
        {
            return builder.getOwner();
        } else {
            return null;
        }

    }

    public Optional<Player> checkSpecialWinCondition(){return null;}
}
