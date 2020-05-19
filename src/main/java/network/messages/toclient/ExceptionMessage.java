package network.messages.toclient;

import network.messages.Message;
import view.ViewManager;

public class ExceptionMessage implements Message<ViewManager> {
    private Exception exception;

    public ExceptionMessage(Exception exception) {
        this.exception = exception;
    }

    @Override
    public void execute(ViewManager target) {
    }
}
