package network.messages.toserver;

import network.messages.Message;
import view.RemoteView;

public class DisconnectServerMessage implements Message<RemoteView> {
    @Override
    public void execute(RemoteView target) {
        target.getController().game().disconnectPlayer(target);
    }
}
