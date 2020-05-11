package network.messages.toclient;

import model.Player;
import network.messages.Message;
import view.ClientView;

public class ActivePlayerMessage implements Message<ClientView> {
    private final Player activePlayer;

    public ActivePlayerMessage(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    @Override
    public void execute(ClientView target) {
        target.receiveActivePlayer(activePlayer);
    }
}
