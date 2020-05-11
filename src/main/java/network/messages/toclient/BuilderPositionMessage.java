package network.messages.toclient;

import model.Builder;
import network.messages.Message;
import view.ClientView;

import java.util.List;

public class BuilderPositionMessage implements Message<ClientView> {
    private final List<Builder> builders;

    public BuilderPositionMessage(List<Builder> builders) {
        this.builders = builders;
    }

    @Override
    public void execute(ClientView target) {
        target.receiveBuildersPositions(builders);
    }
}
