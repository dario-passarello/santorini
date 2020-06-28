package network.messages.toclient;

import model.Builder;
import network.messages.Message;
import utils.Coordinate;
import view.screens.Screen;

import java.util.List;

/**
 *   A message containing the list of {@link model.Square} objects where a {@link Builder} can perform his actions
 */
public class AllowedSquaresMessage implements Message<Screen> {
    private  Builder builder;
    private final List<Coordinate> allowedSquares;
    private boolean specialPower;

    /**
     * Creates an instance of the message
     * @param builder The builder
     * @param allowedSquares The squares where the builder could perform his action
     * @param specialPower True if the squares list refers to a special power action
     */
    public AllowedSquaresMessage(Builder builder, List<Coordinate> allowedSquares, boolean specialPower) {
        this.builder = builder;
        this.allowedSquares = allowedSquares;
        this.specialPower = specialPower;
    }

    /**
     * Create an anonymous instance that not contains a reference to a builder (for the builder placement phase)
     * @param allowedSquares A list of allowed Squares
     */
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
