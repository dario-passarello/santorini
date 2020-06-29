package network.messages.toclient;

import model.gods.God;
import network.messages.Message;
import view.screens.Screen;

import java.util.ArrayList;
import java.util.List;

/**
 * A message to the client containing the Available Gods Message
 */
public class AvailableGodsMessage implements Message<Screen> {
    private final List<God> availableGods;

    /**
     * Creates an AvailableGodsMessage
     * @param availableGods A list of available gods
     */
    public AvailableGodsMessage(List<God> availableGods) {
        this.availableGods = availableGods;
    }

    @Override
    public void execute(Screen target) {
        target.receiveAvailableGodList(new ArrayList<>(availableGods));
    }
}
