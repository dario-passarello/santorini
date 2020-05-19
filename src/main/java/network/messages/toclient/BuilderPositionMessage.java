package network.messages.toclient;

import model.Builder;
import network.messages.Message;
import view.ViewManager;

import java.util.List;

public class BuilderPositionMessage implements Message<ViewManager> {
    private final List<Builder> builders;

    public BuilderPositionMessage(List<Builder> builders) {
        this.builders = builders;
    }

    @Override
    public void execute(ViewManager target) {
        target.receiveBuildersPositions(builders);
    }
}
