package model.wincondition;

//TODO
public abstract class WinConditionDecorator implements WinCondition {
    WinCondition wrappedWinCondition;

    public WinCondition getWrappedWinCondition() {
        return wrappedWinCondition;
    }
}
