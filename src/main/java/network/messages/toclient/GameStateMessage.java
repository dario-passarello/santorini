package network.messages.toclient;

import model.Game;
import model.GameObserver;
import model.Player;
import network.messages.Message;
import view.ClientView;

import java.util.function.Consumer;

public class GameStateMessage implements Message<ClientView> {

    private final Game.State state;
    private final Player activePlayerName;

    public GameStateMessage(Game.State state, Player activePlayerName) {
        this.state = state;
        this.activePlayerName = activePlayerName;
    }

    @Override
    public void execute(ClientView target) {
        target.receiveGameState(state, activePlayerName);
    }
}
