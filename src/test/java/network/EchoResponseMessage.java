package network;

import network.messages.Message;
import view.ViewManager;

public class EchoResponseMessage implements Message<ViewManager> {

    private final String payload;

    public EchoResponseMessage(String payload) {
        this.payload = payload;
    }

    @Override
    public void execute(ViewManager target) {
        //DO NOTHING
    }

    public String getPayload() {
        return payload;
    }
}
