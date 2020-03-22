package model.movebehaviors;
//TODO
public abstract class MoveDecorator implements MoveBehavior {
    private MoveBehavior wrappedBehavior;

    public MoveBehavior getWrappedBehavior() {
        return wrappedBehavior;
    }

    public void setWrappedBehavior(MoveBehavior moveBehavior){
        wrappedBehavior = moveBehavior;
    }
}

