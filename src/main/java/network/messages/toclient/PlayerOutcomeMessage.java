package network.messages.toclient;

import model.Player;
import network.messages.Message;
import view.ViewManager;

public class PlayerOutcomeMessage implements Message<ViewManager> {

    private final Player player;
    private final boolean winner;


    public PlayerOutcomeMessage(Player player, boolean winner) {
        this.player = player;
        this.winner = winner;
    }


    @Override
    public void execute(ViewManager target) {
        target.receivePlayerOutcome(player, winner);
    }
}
