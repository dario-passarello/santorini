package network.messages.toserver;

import network.messages.Message;
import view.RemoteView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubmitGodListMessage implements Message<RemoteView> {

    private final Set<String> godList;

    public SubmitGodListMessage(List<String> gods){
        godList = new HashSet<>(gods);
    }

    @Override
    public void execute(RemoteView target) {
        target.getController().game().submitGodList(target,godList);
    }
}
