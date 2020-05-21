package network.messages.toclient;

import model.Game;
import model.Player;
import network.messages.Message;
import view.screens.Screen;

public class GameStateMessage implements Message<Screen> {

    private final Game.State state;
    private final Player activePlayerName;

    public GameStateMessage(Game.State state, Player activePlayerName) {
        this.state = state;
        this.activePlayerName = activePlayerName;
    }

    @Override
    public void execute(Screen target) {
        target.receiveGameState(state, activePlayerName);
    }
}
