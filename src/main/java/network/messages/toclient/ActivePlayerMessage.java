package network.messages.toclient;

import model.Player;
import network.messages.Message;
import view.screens.Screen;

/**
 * A message to the client containing the object of the active player during the current turn
 */
public class ActivePlayerMessage implements Message<Screen> {
    private final Player activePlayer;

    /**
     * Creates an ActivePlayerMessage
     * @param activePlayer The active player object
     */
    public ActivePlayerMessage(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    @Override
    public void execute(Screen target) {
        target.receiveActivePlayer(activePlayer);
    }
}
