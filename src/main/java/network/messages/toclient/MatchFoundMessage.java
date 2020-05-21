package network.messages.toclient;

import network.messages.Message;
import view.screens.Screen;

import java.util.List;

public class MatchFoundMessage implements Message<Screen> {
    String assignedUsername;
    List<String> players;
    public MatchFoundMessage(String assignedUsername, List<String> players){
        this.assignedUsername = assignedUsername;
        this.players = players;
    }
    @Override
    public void execute(Screen target) {
        //TODO
    }
}
