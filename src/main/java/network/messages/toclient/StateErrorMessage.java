package network.messages.toclient;

import model.StateIdentifier;
import network.messages.Message;
import view.screens.Screen;

/**
 * A message sent to the client when a controller call is legit, but made in the wrong model state
 * @param <T> The state identfier
 */
public class StateErrorMessage<T extends StateIdentifier> implements Message<Screen> {

    /**
     * Creates a StateErrorMessage
     * @param currentState State where the error has occourred
     */
    public StateErrorMessage(T currentState) {
    }

    @Override
    public void execute(Screen target) {
    }
}
