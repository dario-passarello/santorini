package network.messages.toserver;

import network.messages.Message;
import utils.Coordinate;
import view.RemoteView;

/**
 * A message containing the coordinate of the square in where the builder is placed
 */
public class PlaceBuilderMessage implements Message<RemoteView> {

    private final Coordinate choice;

    /**
     * Creates a new PlaceBuilderMessage instance
     * @param choice The coordinate where the builder should be placed
     */
    public PlaceBuilderMessage(Coordinate choice){
        this.choice = choice;
    }

    @Override
    public void execute(RemoteView target) {
        target.getController().game().placeBuilder(target, choice);
    }
}
