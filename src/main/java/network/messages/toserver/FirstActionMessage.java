package network.messages.toserver;

import utils.Coordinate;
import view.RemoteView;
import network.messages.Message;

/**
 * A message to the Server containing the first action of the turn made from the player
 */
public class FirstActionMessage implements Message<RemoteView> {

    private final int builder;
    private final Coordinate coordinate;
    private final boolean firstPower;

    /**
     * Creates a FirstActionMessage instance
     * @param builderID The ID of the builder to move
     * @param coordinate The coordinate where the builder wants to move
     * @param firstPower True if a power at the start of the turn is used
     */
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
