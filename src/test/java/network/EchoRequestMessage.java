package network;

import network.messages.Message;
import view.RemoteView;

public class EchoRequestMessage implements Message<RemoteView> {
    private final String payload;

    public EchoRequestMessage(String payload) {
        this.payload = payload;
    }

    @Override
    public void execute(RemoteView target) {
        target.sendMessage(new EchoResponseMessage(payload));
    }
}
