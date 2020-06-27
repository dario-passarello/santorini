package model.wincondition;

import model.Board;
import model.Builder;
import model.Player;
import model.Square;

import java.util.Optional;


/**
 * It's the abstract Win Condition decorator (all the concrete win conditions must extend this class)
 */
public abstract class WinConditionDecorator implements WinCondition {

    protected WinCondition wrappedWinCondition;

    public abstract Optional<Player> checkWinCondition(Square start, Builder builder);

    public abstract Optional<Player> checkSpecialWinCondition(Board board);
}
