package network.messages.toclient;

import model.Game;
import model.Player;
import network.messages.Message;
import view.screens.Screen;

/**
 * A message to the client containing the actual {@link model.gamestates.GameState} and the active player
 */
public class GameStateMessage implements Message<Screen> {

    private final Game.State state;
    private final Player activePlayerName;

    /**
     * Creates a GameStateMessage instance
     * @param state The {@link Game.State} object linked to the current GameState
     * @param activePlayerName The name of the active player (the player that should make a choice)
     */
    public GameStateMessage(Game.State state, Player activePlayerName) {
        this.state = state;
        this.activePlayerName = activePlayerName;
    }

    @Override
    public void execute(Screen target) {
        target.receiveGameState(state, activePlayerName);
    }
}
