package network.messages.toclient;

import model.Turn;
import network.messages.Message;
import view.screens.Screen;

public class TurnStateMessage implements Message<Screen> {
    private final Turn.State turnState;

    public TurnStateMessage(Turn.State turnState) {
        this.turnState = turnState;
    }

    @Override
    public void execute(Screen target) {
        target.receiveStateChange(turnState);
    }
}
