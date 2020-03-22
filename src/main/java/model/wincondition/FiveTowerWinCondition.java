package model.wincondition;

//TODO
public class FiveTowerWinCondition extends WinConditionDecorator {

    public FiveTowerWinCondition(WinCondition winCondition){
        setWrappedWinCondition(winCondition);
    }

    public boolean checkWinCondition() {
        return false;
    }
}
