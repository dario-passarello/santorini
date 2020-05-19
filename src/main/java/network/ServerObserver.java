package network;

import network.messages.Message;
import utils.Observer;
import view.ViewManager;

public interface ServerObserver extends Observer {

    void notifyMessage(Message<ViewManager> clientViewMessage);

}
