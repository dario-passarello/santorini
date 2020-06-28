package model.wincondition;

import model.Board;
import model.Builder;
import model.Player;
import model.Square;

import java.util.Optional;


/**
 *  Contains the strategies for finding the winning conditions.
 *  Created using the decorator pattern. {@link StandardWinCondition} is the standard win condition,
 *  custom win conditions could decorate the Win Condition
 */
public interface WinCondition {
    /**
     * @param start is the initial position of the current builder (before move)
     * @param builder is the current builder
     * @return true if a win condition occurred after the move phase, otherwise false
     */
    Optional<Player> checkWinCondition(Square start, Builder builder);

    /**
     * @param board The board of the game
     * @return true if a win condition occurred after the build phase, otherwise false
     */
    Optional<Player> checkSpecialWinCondition(Board board);
}
