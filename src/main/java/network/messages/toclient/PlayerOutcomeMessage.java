package network.messages.toclient;

import model.Player;
import network.messages.Message;
import view.screens.Screen;

public class PlayerOutcomeMessage implements Message<Screen> {

    private final Player player;
    private final boolean winner;


    public PlayerOutcomeMessage(Player player, boolean winner) {
        this.player = player;
        this.winner = winner;
    }


    @Override
    public void execute(Screen target) {
        target.receivePlayerOutcome(player, winner);
    }
}
