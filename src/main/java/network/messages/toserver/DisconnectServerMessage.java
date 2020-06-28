package network.messages.toserver;

import network.messages.Message;
import view.RemoteView;

/**
 * A message sent to the Server when the client wants to disconnect
 */
public class DisconnectServerMessage implements Message<RemoteView> {
    @Override
    public void execute(RemoteView target) {
        target.getController().game().disconnectPlayer(target);
    }
}
