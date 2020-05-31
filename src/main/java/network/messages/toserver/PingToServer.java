package network.messages.toserver;

import network.ClientHandler;
import network.messages.Message;

public class PingToServer implements Message<ClientHandler> {

    public final int a = 2;

    @Override
    public void execute(ClientHandler target) {
        //Does nothing, it's a simple ping message
    }
}
