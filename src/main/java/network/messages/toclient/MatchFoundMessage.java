package network.messages.toclient;

import network.messages.Message;
import view.screens.Screen;

import java.util.ArrayList;
import java.util.List;

public class MatchFoundMessage implements Message<Screen> {
    private final String assignedUsername;
    private final List<String> players;
    public MatchFoundMessage(String assignedUsername, List<String> players){
        this.assignedUsername = assignedUsername;
        this.players = players;
    }
    @Override
    public void execute(Screen target) {
        target.receiveMatchFound(assignedUsername,new ArrayList<>(players));
    }
}
