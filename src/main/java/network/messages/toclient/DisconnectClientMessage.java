package network.messages.toclient;

import network.messages.Message;
import view.screens.Screen;

/**
 * A message to the client sent from the server before disconnecting
 */
public class DisconnectClientMessage implements Message<Screen> {


    @Override
    public void execute(Screen target) {
        target.receiveDisconnect();
    }
}
