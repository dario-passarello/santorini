package network.messages.toclient;

import model.StateIdentifier;
import network.messages.Message;
import view.ClientView;

public class StateErrorMessage<T extends StateIdentifier> implements Message<ClientView> {

    private final T currentState;

    public StateErrorMessage(T currentState) {
        this.currentState = currentState;
    }

    @Override
    public void execute(ClientView target) {
        //TODO
    }
}
