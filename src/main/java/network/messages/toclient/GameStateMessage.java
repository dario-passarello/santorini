package network.messages.toclient;

import model.Game;
import model.Player;
import network.messages.Message;
import view.ViewManager;

public class GameStateMessage implements Message<ViewManager> {

    private final Game.State state;
    private final Player activePlayerName;

    public GameStateMessage(Game.State state, Player activePlayerName) {
        this.state = state;
        this.activePlayerName = activePlayerName;
    }

    @Override
    public void execute(ViewManager target) {
        target.receiveGameState(state, activePlayerName);
    }
}
