package model.wincondition;

import model.Board;
import model.Builder;
import model.Player;
import model.Square;

import java.util.Optional;

public class NoPerimeterWinCondition extends WinConditionDecorator{

    public  NoPerimeterWinCondition(WinCondition winCondition){
        wrappedWinCondition = winCondition;
    }

    /**
     * @param start is the initial position of the current builder (before move)
     * @param builder is the current builder
     * @return true accomplish a normal win condition with a malus (you can't win in the perimeter)
     */
    public Optional<Player> checkWinCondition(Square start, Builder builder){
        if(builder.getPosition().getCoordinate().getX() == 1 ||
                builder.getPosition().getCoordinate().getX() == 5 ||
                builder.getPosition().getCoordinate().getY() == 1 ||
                builder.getPosition().getCoordinate().getY() == 5){
            return Optional.empty();
        } else {
            return wrappedWinCondition.checkWinCondition(start, builder);
        }
    }

    public Optional<Player> checkSpecialWinCondition(Board board) {return wrappedWinCondition.checkSpecialWinCondition(board);}
}
