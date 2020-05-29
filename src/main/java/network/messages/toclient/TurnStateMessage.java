package network.messages.toclient;

import model.Player;
import model.Turn;
import network.messages.Message;
import view.screens.Screen;

public class TurnStateMessage implements Message<Screen> {
    private final Turn.State turnState;
    private final Player activePlayer;

    public TurnStateMessage(Turn.State turnState, Player activePlayer1) {
        this.turnState = turnState;
        this.activePlayer = activePlayer1;
    }

    @Override
    public void execute(Screen target) {
        target.receiveTurnState(turnState, activePlayer);
    }
}
