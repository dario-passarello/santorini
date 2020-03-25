package model.wincondition;

//TODO
public class FiveTowerWinCondition extends WinConditionDecorator {

    public FiveTowerWinCondition(WinCondition winCondition){
        wrappedWinCondition = winCondition;
    }

    public boolean checkWinCondition() {
        return false;
    }
}
