package network.messages.toserver;

import network.messages.Message;
import view.RemoteView;

public class EndPhaseMessage implements Message<RemoteView> {

    @Override
    public void execute(RemoteView target) {
        target.getController().turn().endPhase(target);
    }
}
