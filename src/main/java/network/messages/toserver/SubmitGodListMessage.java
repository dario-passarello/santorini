package network.messages.toserver;

import network.messages.Message;
import view.RemoteView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Message sent to the server containing the god to use for the game
 */
public class SubmitGodListMessage implements Message<RemoteView> {

    private final Set<String> godList;

    /**
     * Create a new instace
     * @param gods List of gods
     */
    public SubmitGodListMessage(List<String> gods){
        godList = new HashSet<>(gods);
    }

    @Override
    public void execute(RemoteView target) {
        target.getController().game().submitGodList(target,godList);
    }
}
