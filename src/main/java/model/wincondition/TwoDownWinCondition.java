package model.wincondition;

//TODO
public class TwoDownWinCondition extends WinConditionDecorator {

    public  TwoDownWinCondition(WinCondition winCondition){
        setWrappedWinCondition(winCondition);
    }

    public boolean checkWinCondition() {
        return false;
    }
}
