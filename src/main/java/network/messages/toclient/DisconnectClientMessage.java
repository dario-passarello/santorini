package network.messages.toclient;

import network.messages.Message;
import view.screens.Screen;

public class DisconnectClientMessage implements Message<Screen> {


    @Override
    public void execute(Screen target) {
        target.receiveDisconnect();
    }
}
