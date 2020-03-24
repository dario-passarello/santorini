package model.movebehaviors;
//TODO
public abstract class MoveDecorator implements MoveBehavior {
    private MoveBehavior wrappedBehavior;

    public MoveBehavior getWrappedBehavior() {
        return wrappedBehavior;
    }

    protected void setWrappedBehavior(MoveBehavior moveBehavior){
        wrappedBehavior = moveBehavior;
    }
}

