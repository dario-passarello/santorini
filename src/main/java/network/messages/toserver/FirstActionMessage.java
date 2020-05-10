package network.messages.toserver;

import utils.Coordinate;
import view.RemoteView;
import network.messages.Message;

public class FirstActionMessage implements Message<RemoteView> {

    private final int builder;
    private final Coordinate coordinate;
    private final boolean firstPower;


    public FirstActionMessage(int builderID, Coordinate coordinate, boolean firstPower) {
        this.builder = builderID;
        this.coordinate = coordinate;
        this.firstPower = firstPower;
    }

    @Override
    public void execute(RemoteView target) {
        target.getController().turn().firstMove(target, builder, coordinate, firstPower);
    }


}
