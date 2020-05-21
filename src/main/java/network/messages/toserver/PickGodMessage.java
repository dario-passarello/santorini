package network.messages.toserver;

import network.messages.Message;
import view.RemoteView;

public class PickGodMessage implements Message<RemoteView> {

    private final String godName;

    public PickGodMessage(String godName){
        this.godName = godName;
    }

    @Override
    public void execute(RemoteView target) {
        target.getController().game().pickGod(target,godName);
    }
}
