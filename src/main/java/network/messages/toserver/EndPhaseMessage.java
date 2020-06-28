package network.messages.toserver;

import network.messages.Message;
import view.RemoteView;

/**
 * A message sent when the player wants to end his phase without doing any additional move/build
 */
public class EndPhaseMessage implements Message<RemoteView> {

    @Override
    public void execute(RemoteView target) {
        target.getController().turn().endPhase(target);
    }
}
