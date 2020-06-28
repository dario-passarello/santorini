package network.messages.toserver;

import network.ClientHandler;
import network.messages.Message;

/**
 * A ping from the client to the server used to check if the connection to the server is alive
 */
public class PingToServer implements Message<ClientHandler> {

    public final int a = 2;

    @Override
    public void execute(ClientHandler target) {
        //Does nothing, it's a simple ping message
    }
}
