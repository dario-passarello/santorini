package network.messages.toserver;

import network.ClientHandler;
import network.messages.Message;

public class LoginDataMessage implements Message<ClientHandler> {
    private final String username;
    private final int playerNumber;
    public LoginDataMessage(String username, int playerNumber) {
        this.username = username;
        this.playerNumber = playerNumber;
    }

    @Override
    public void execute(ClientHandler target) {
        target.setPlayerNumber(playerNumber);
        target.setName(username);
    }
}
