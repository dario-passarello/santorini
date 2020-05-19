package network.messages.toclient;

import network.messages.Message;
import view.ViewManager;

import java.util.List;

public class MatchFoundMessage implements Message<ViewManager> {
    String assignedUsername;
    List<String> players;
    public MatchFoundMessage(String assignedUsername, List<String> players){
        this.assignedUsername = assignedUsername;
        this.players = players;
    }
    @Override
    public void execute(ViewManager target) {
        //TODO
    }
}
