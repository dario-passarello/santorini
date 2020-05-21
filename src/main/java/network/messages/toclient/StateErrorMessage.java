package network.messages.toclient;

import model.StateIdentifier;
import network.messages.Message;
import view.screens.Screen;

public class StateErrorMessage<T extends StateIdentifier> implements Message<Screen> {

    private final T currentState;

    public StateErrorMessage(T currentState) {
        this.currentState = currentState;
    }

    @Override
    public void execute(Screen target) {
        //TODO
    }
}
