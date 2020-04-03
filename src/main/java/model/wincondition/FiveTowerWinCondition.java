package model.wincondition;

import model.Board;
import model.Builder;
import model.Player;
import model.Square;

import java.util.Optional;

//TODO
public class FiveTowerWinCondition extends WinConditionDecorator {

    private Player chronus;

    public FiveTowerWinCondition(WinCondition winCondition, Player chronus){
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
        return null;
    }

    public Optional<Player> checkSpecialWinCondition() {
        Board board = null; //TODO
        int counter = 0;
        for(int i = 1; i <= board.BOARD_SIZE; i++){                 //need BOARD_SIZE
            for(int j = 1; j <= board.BOARD_SIZE; j++){
                if(board.squareAt(i,j).getBuildLevel() == 3 && board.squareAt(i,j).isDomed()){
                    counter++;
                }
            }
        }
        if(counter >= 5){
            return Optional.of(chronus);                 //need Chronus Player reference
        }
        else {
            return Optional.empty();
        }

        //To wrap
    }
}
