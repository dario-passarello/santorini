package model.wincondition;

import model.Builder;
import model.Player;
import model.Square;

import java.util.Optional;

//TODO
public abstract class WinConditionDecorator implements WinCondition {
    protected WinCondition wrappedWinCondition;
    public abstract Optional<Player> checkWinCondition(Square start, Builder builder);
    public abstract Optional<Player> checkSpecialWinCondition();
}
