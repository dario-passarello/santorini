package network.messages.toclient;

import model.StateIdentifier;
import network.messages.Message;
import view.ViewManager;

public class StateErrorMessage<T extends StateIdentifier> implements Message<ViewManager> {

    private final T currentState;

    public StateErrorMessage(T currentState) {
        this.currentState = currentState;
    }

    @Override
    public void execute(ViewManager target) {
        //TODO
    }
}
