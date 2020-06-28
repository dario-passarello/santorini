package network.messages.toclient;

import model.Game;
import model.Player;
import model.Turn;
import network.messages.Message;
import view.screens.Screen;

/**
 * A message to the client containing the actual {@link model.turnstates.TurnState} and the active player
 */
public class TurnStateMessage implements Message<Screen> {
    private final Turn.State turnState;
    private final Player activePlayer;

    /**
     * Creates a GameStateMessage instance
     * @param turnState The {@link Turn.State} object linked to the current GameState
     * @param activePlayer1 The name of the active player (the player that should make a choice)
     */
    public TurnStateMessage(Turn.State turnState, Player activePlayer1) {
        this.turnState = turnState;
        this.activePlayer = activePlayer1;
    }

    @Override
    public void execute(Screen target) {
        target.receiveTurnState(turnState, activePlayer);
    }
}
