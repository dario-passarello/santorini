package network.messages.toclient;

import network.messages.Message;
import view.ClientView;

import java.util.List;

public class MatchFoundMessage implements Message<ClientView> {
    String assignedUsername;
    List<String> players;
    public MatchFoundMessage(String assignedUsername, List<String> players){
        this.assignedUsername = assignedUsername;
        this.players = players;
    }
    @Override
    public void execute(ClientView target) {
        //TODO
    }
}
