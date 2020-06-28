package network.messages.toclient;

import model.Builder;
import network.messages.Message;
import view.screens.Screen;

import java.util.List;

/**
 * A Message to the client containing a list of all builders in the board
 */
public class BuilderPositionMessage implements Message<Screen> {
    private final List<Builder> builders;

    /**
     * Creates an instance of BuilderPositionMessage
     * @param builders A list of builders
     */
    public BuilderPositionMessage(List<Builder> builders) {
        this.builders = builders;
    }

    @Override
    public void execute(Screen target) {
        target.receiveBuildersPositions(builders);
    }
}
