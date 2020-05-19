package network.messages.toclient;

import network.messages.Message;
import view.ViewManager;

public class UpdateDoneMessage implements Message<ViewManager> {

    @Override
    public void execute(ViewManager target) {
        target.receiveUpdateDone();
    }
}
