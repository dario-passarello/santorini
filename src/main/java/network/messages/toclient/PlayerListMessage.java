package network.messages.toclient;

import model.Player;
import network.messages.Message;
import view.screens.Screen;

import java.util.ArrayList;
import java.util.List;

/**
 * A message to the client containing the list of {@link Player} objects. With this message informations about player's god
 * are also sent
 */
public class PlayerListMessage implements Message<Screen> {
    private final List<Player> playerList;

    /**
     * Creates a new PlayerListMessage instace
     * @param playerList The list of all players in the game
     */
    public PlayerListMessage(List<Player> playerList) {
        this.playerList = playerList;
    }

    @Override
    public void execute(Screen target) {
        target.receivePlayerList(new ArrayList<>(playerList));
    }
}
