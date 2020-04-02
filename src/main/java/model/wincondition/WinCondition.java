package model.wincondition;

import model.Builder;
import model.Player;
import model.Square;

import java.util.Optional;

//TODO
public interface WinCondition {
    /**
     * @param start is the initial position of the current builder (before move)
     * @param builder is the current builder
     * @return true if a win condition occurred after the move phase, otherwise false
     */
    Optional<Player> checkWinCondition(Square start, Builder builder);

    /**
     * @return true if a win condition occurred after the build phase, otherwise false
     */
    Optional<Player> checkSpecialWinCondition();
}
