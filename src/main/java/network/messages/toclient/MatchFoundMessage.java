package network.messages.toclient;

import network.messages.Message;
import view.screens.Screen;

import java.util.ArrayList;
import java.util.List;

/**
 * A message to the client sent when a match is found.
 * This message contains the names of the other players and the new name for the player
 * in case the login username was already taken
 */
public class MatchFoundMessage implements Message<Screen> {
    private final String assignedUsername;
    private final List<String> players;

    /**
     * Creates a MatchFoundMessage
     * @param assignedUsername The new username for the player (could be equal to the old in case of no duplicates)
     * @param players The list of all player objects in game
     */
    public MatchFoundMessage(String assignedUsername, List<String> players){
        this.assignedUsername = assignedUsername;
        this.players = players;
    }
    @Override
    public void execute(Screen target) {
        target.receiveMatchFound(assignedUsername,new ArrayList<>(players));
    }
}
