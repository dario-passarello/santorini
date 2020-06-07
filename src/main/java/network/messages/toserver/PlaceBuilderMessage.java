package network.messages.toserver;

import network.messages.Message;
import utils.Coordinate;
import view.RemoteView;

public class PlaceBuilderMessage implements Message<RemoteView> {

    private final Coordinate choice;

    public PlaceBuilderMessage(Coordinate choice){
        this.choice = choice;
    }

    @Override
    public void execute(RemoteView target) {
        target.getController().game().placeBuilder(target, choice);
    }
}
