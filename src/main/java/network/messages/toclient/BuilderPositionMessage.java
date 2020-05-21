package network.messages.toclient;

import model.Builder;
import network.messages.Message;
import view.screens.Screen;

import java.util.List;

public class BuilderPositionMessage implements Message<Screen> {
    private final List<Builder> builders;

    public BuilderPositionMessage(List<Builder> builders) {
        this.builders = builders;
    }

    @Override
    public void execute(Screen target) {
        target.receiveBuildersPositions(builders);
    }
}
