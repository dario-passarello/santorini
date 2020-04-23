package model.wincondition;

import model.Board;
import model.Builder;
import model.Player;
import model.Square;
import model.gods.God;

import java.util.Optional;

//TODO
public class FiveTowerWinCondition extends WinConditionDecorator {

    private God chronus;

    public FiveTowerWinCondition(WinCondition winCondition, God chronus){
        wrappedWinCondition = winCondition;
    }

    /**
     * @param start   is the initial position of the current builder (before move)
     * @param builder is the current builder
     * @return true if a win condition occurred after the move phase, otherwise false
     */
    @Override
    public Optional<Player> checkWinCondition(Square start, Builder builder) {
        //TODO
        return wrappedWinCondition.checkWinCondition(start, builder);
    }

    public Optional<Player> checkSpecialWinCondition() {
        Board board = null; //TODO
        int counter = 0;
        for(int i = 0; i <= Board.BOARD_SIZE-1; i++){
            for(int j = 0; j <= Board.BOARD_SIZE-1; j++){
                if(board.squareAt(i,j).getBuildLevel() == 3 && board.squareAt(i,j).isDomed()){
                    counter++;
                }
            }
        }
        if(counter >= 5){
            return Optional.of(chronus.getPlayer());
        }
        else {
            return Optional.empty();
        }

        //To wrap
    }
}
