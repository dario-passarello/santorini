package network.messages.toclient;

import model.Player;
import network.messages.Message;
import view.ClientView;

import java.util.ArrayList;
import java.util.List;

public class PlayerListMessage implements Message<ClientView> {
    private final List<Player> playerList;


    public PlayerListMessage(List<Player> playerList) {
        this.playerList = playerList;
    }

    @Override
    public void execute(ClientView target) {
        target.receivePlayerList(new ArrayList<>(playerList));
    }
}
