package network.messages.toclient;

import model.Turn;
import network.messages.Message;
import view.ViewManager;

public class TurnStateMessage implements Message<ViewManager> {
    private final Turn.State turnState;

    public TurnStateMessage(Turn.State turnState) {
        this.turnState = turnState;
    }

    @Override
    public void execute(ViewManager target) {
        target.receiveStateChange(turnState);
    }
}
