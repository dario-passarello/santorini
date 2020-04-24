package model.wincondition;

import model.Board;
import model.Builder;
import model.Player;
import model.Square;

import java.util.Optional;

//TODO
public class TwoDownWinCondition extends WinConditionDecorator {

    public  TwoDownWinCondition(WinCondition winCondition){
        wrappedWinCondition = winCondition;
    }

    /**
     * @param start is the initial position of the current builder (before move)
     * @param builder is the current builder
     * @return true if accomplish a normal win condition or a special one (going down of two level in a single move)
     */
    public Optional<Player> checkWinCondition(Square start, Builder builder){
        if(start.getBuildLevel() - builder.getPosition().getBuildLevel() >= 2){     //if the special condition is happening
            return Optional.of(builder.getOwner());
        } else {
            return wrappedWinCondition.checkWinCondition(start, builder);
        }
    }

    public Optional<Player> checkSpecialWinCondition(Board board) {return wrappedWinCondition.checkSpecialWinCondition(board);}
}
