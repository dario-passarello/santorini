package model.wincondition;

import model.Board;
import model.Player;

import java.util.Optional;

//TODO
public class FiveTowerWinCondition extends WinConditionDecorator {

    public FiveTowerWinCondition(WinCondition winCondition){
        wrappedWinCondition = winCondition;
    }

    public Optional<Player> checkSpecialWinCondition() {
        return false;
    }

    @Override
    public Optional<Player> checkSpecialWinCondition(Board board) {
        int counter = 0
        for(int i = 1; i <= board.BOARD_SIZE; i++){                 //need BOARD_SIZE
            for(int j = 1; j <= board.BOARD_SIZE; j++){
                if(board.squareAt(i,j).getBuildLevel() == 3 && board.squareAt(i,j).isDomed()){
                    counter++;
                }
            }
        }
        if(counter >= 5){
            return null;                 //need Chronus Player reference
        }
        else {
            return null;
        }
    }
}
