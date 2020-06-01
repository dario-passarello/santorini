package network.messages.toclient;

import model.Builder;
import network.messages.Message;
import utils.Coordinate;
import view.screens.Screen;

import java.util.List;

public class AllowedSquaresMessage implements Message<Screen> {
    private  Builder builder;
    private final List<Coordinate> allowedSquares;
    private boolean specialPower;

    public AllowedSquaresMessage(Builder builder, List<Coordinate> allowedSquares, boolean specialPower) {
        this.builder = builder;
        this.allowedSquares = allowedSquares;
        this.specialPower = specialPower;
    }

    public AllowedSquaresMessage(List<Coordinate> allowedSquares){
        this.allowedSquares = allowedSquares;
    }


    @Override
    public void execute(Screen target) {
        if(builder != null){
            target.receiveAllowedSquares(builder, allowedSquares, specialPower);
        } else {
            target.receiveAllowedSquares(allowedSquares);
        }

    }
}
