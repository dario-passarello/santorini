package network.messages.toserver;

import network.ClientHandler;
import network.messages.Message;

/**
 * The first message sent to the server containing the username and the number of players of the game
 */
public class LoginDataMessage implements Message<ClientHandler> {
    private final String username;
    private final int playerNumber;

    /**
     * Creates a LoginDataMessage instance
     * @param username Username chosen by the client
     * @param playerNumber Number of player in the game
     */
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
