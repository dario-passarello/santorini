package network.messages.toclient;

import network.messages.Message;
import view.ClientView;

public class UpdateDoneMessage implements Message<ClientView> {

    @Override
    public void execute(ClientView target) {
        target.receiveUpdateDone();
    }
}
