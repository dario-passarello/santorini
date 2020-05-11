package network.messages.toclient;

import model.Builder;
import network.messages.Message;
import utils.Coordinate;
import view.ClientView;

import java.util.List;

public class AllowedSquaresMessage implements Message<ClientView> {
    private final Builder builder;
    private final List<Coordinate> allowedSquares;
    private final boolean specialPower;

    public AllowedSquaresMessage(Builder builder, List<Coordinate> allowedSquares, boolean specialPower) {
        this.builder = builder;
        this.allowedSquares = allowedSquares;
        this.specialPower = specialPower;
    }


    @Override
    public void execute(ClientView target) {
        target.receiveAllowedSquares(builder, allowedSquares, specialPower);
    }
}
