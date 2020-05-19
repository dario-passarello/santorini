package network.messages.toclient;

import model.Player;
import network.messages.Message;
import view.ViewManager;

public class ActivePlayerMessage implements Message<ViewManager> {
    private final Player activePlayer;

    public ActivePlayerMessage(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    @Override
    public void execute(ViewManager target) {
        target.receiveActivePlayer(activePlayer);
    }
}
