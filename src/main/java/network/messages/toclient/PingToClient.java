package network.messages.toclient;

import network.ServerAdapter;
import network.messages.Message;
import view.screens.Screen;

public class PingToClient implements Message<Screen> {


    @Override
    public void execute(Screen target) {
        //Do nothing, it's only a ping
    }
}
