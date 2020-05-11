package network.messages.toclient;

import model.Player;
import network.messages.Message;
import view.ClientView;

public class PlayerOutcomeMessage implements Message<ClientView> {

    private final Player player;
    private final boolean winner;


    public PlayerOutcomeMessage(Player player, boolean winner) {
        this.player = player;
        this.winner = winner;
    }


    @Override
    public void execute(ClientView target) {
        target.receivePlayerOutcome(player, winner);
    }
}
