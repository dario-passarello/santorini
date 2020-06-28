package network.messages.toserver;

import network.messages.Message;
import view.RemoteView;

/**
 * A message to the server containing the name of the god picked from the player
 */
public class PickGodMessage implements Message<RemoteView> {

    private final String godName;

    /**
     * Creates a new PickGodMessage instance
     * @param godName A String containing the name of the god picked from the player
     */
    public PickGodMessage(String godName){
        this.godName = godName;
    }

    @Override
    public void execute(RemoteView target) {
        target.getController().game().pickGod(target,godName);
    }
}
