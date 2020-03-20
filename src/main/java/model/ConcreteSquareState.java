package model;

public class ConcreteSquareState extends SquareState {


    public ConcreteSquareState(Integer level, boolean dome) {
        super.level = level;
    }


    public SquareState dome() {
        return null;
    }

    public SquareState build() {
        return null;
    }

    public SquareState remove() {
        return null;
    }
}
