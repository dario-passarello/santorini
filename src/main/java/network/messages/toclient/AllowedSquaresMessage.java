package network.messages.toclient;

import model.Builder;
import network.messages.Message;
import utils.Coordinate;
import view.ViewManager;

import java.util.List;

public class AllowedSquaresMessage implements Message<ViewManager> {
    private final Builder builder;
    private final List<Coordinate> allowedSquares;
    private final boolean specialPower;

    public AllowedSquaresMessage(Builder builder, List<Coordinate> allowedSquares, boolean specialPower) {
        this.builder = builder;
        this.allowedSquares = allowedSquares;
        this.specialPower = specialPower;
    }


    @Override
    public void execute(ViewManager target) {
        target.receiveAllowedSquares(builder, allowedSquares, specialPower);
    }
}
