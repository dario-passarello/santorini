package model.wincondition;

import model.Board;
import model.Builder;
import model.Player;
import model.Square;

import java.util.Optional;

/**
 * It's a Win Condition main component that allows a standard win condition
 * (if one of your builder move up, reaching a building at level 3)
 */
public class StandardWinCondition implements WinCondition {


    /**
     * @param start   is the initial position of the current builder (before move)
     * @param builder is the current builder
     * @return true if the builder moved up, reaching a building at level 3
     */
    @Override
    public Optional<Player> checkWinCondition(Square start, Builder builder) {
        if (builder.getSquare().getBuildLevel() == 3 && start.getBuildLevel() == 2) {
            return Optional.of(builder.getOwner());
        }
        return Optional.empty();
    }
    @Override
    public Optional<Player> checkSpecialWinCondition(Board board) {
        return Optional.empty();
    }
}
