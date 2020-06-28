package network.messages.toclient;

import network.messages.Message;
import view.screens.Screen;

/**
 * The last message sent at every group of message sent from the server
 */
public class UpdateDoneMessage implements Message<Screen> {

    @Override
    public void execute(Screen target) {
        target.receiveUpdateDone();
    }
}
