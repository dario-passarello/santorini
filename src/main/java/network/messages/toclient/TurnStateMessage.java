package network.messages.toclient;

import model.Turn;
import network.messages.Message;
import view.ClientView;

public class TurnStateMessage implements Message<ClientView> {
    private final Turn.State turnState;

    public TurnStateMessage(Turn.State turnState) {
        this.turnState = turnState;
    }

    @Override
    public void execute(ClientView target) {
        target.receiveStateChange(turnState);
    }
}
