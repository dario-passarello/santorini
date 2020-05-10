package network.messages.toclient;

import network.messages.Message;
import view.ClientView;

public class ExceptionMessage implements Message<ClientView> {
    private Exception exception;

    public ExceptionMessage(Exception exception) {
        this.exception = exception;
    }

    @Override
    public void execute(ClientView target) {
    }
}
