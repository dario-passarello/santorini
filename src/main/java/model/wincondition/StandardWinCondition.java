package model.wincondition;

import model.Board;
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
            return Optional.of(builder.getOwner());
        } else {
            return Optional.empty();
        }

    }

    public Optional<Player> checkSpecialWinCondition(Board board){return Optional.empty();}
}
