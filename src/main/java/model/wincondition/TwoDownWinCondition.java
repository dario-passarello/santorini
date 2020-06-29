package model.wincondition;

import model.Board;
import model.Builder;
import model.Player;
import model.Square;

import java.util.Optional;

/**
 * It's a Win Condition decorator that allows to win going down of two or more level in a single move
 */
public class TwoDownWinCondition extends WinConditionDecorator {

    /**
     * The constructor method. It decorates the parameter with this class
     * @param winCondition The Win Condition target
     */
    public TwoDownWinCondition(WinCondition winCondition) {
        wrappedWinCondition = winCondition;
    }

    /**
     * @param start   is the initial position of the current builder (before move)
     * @param builder is the current builder
     * @return true if accomplish a normal win condition or a special one (going down of two or more level in a single move)
     */
    @Override
    public Optional<Player> checkWinCondition(Square start, Builder builder) {
        if (start.getBuildLevel() - builder.getSquare().getBuildLevel() >= 2) {     //if the special condition is happening
            return Optional.of(builder.getOwner());
        } else {
            return wrappedWinCondition.checkWinCondition(start, builder);
        }
    }

    @Override
    public Optional<Player> checkSpecialWinCondition(Board board) {
        return wrappedWinCondition.checkSpecialWinCondition(board);
    }
}
