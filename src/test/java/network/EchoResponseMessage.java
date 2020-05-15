package network;

import network.messages.Message;
import view.ClientView;

public class EchoResponseMessage implements Message<ClientView> {

    private final String payload;

    public EchoResponseMessage(String payload) {
        this.payload = payload;
    }

    @Override
    public void execute(ClientView target) {
        //DO NOTHING
    }

    public String getPayload() {
        return payload;
    }
}
