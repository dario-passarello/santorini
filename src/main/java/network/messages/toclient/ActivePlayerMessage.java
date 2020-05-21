package network.messages.toclient;

import model.Player;
import network.messages.Message;
import view.screens.Screen;

public class ActivePlayerMessage implements Message<Screen> {
    private final Player activePlayer;

    public ActivePlayerMessage(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    @Override
    public void execute(Screen target) {
        target.receiveActivePlayer(activePlayer);
    }
}
