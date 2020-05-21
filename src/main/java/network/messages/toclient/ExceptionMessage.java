package network.messages.toclient;

import network.messages.Message;
import view.screens.Screen;

public class ExceptionMessage implements Message<Screen> {
    private Exception exception;

    public ExceptionMessage(Exception exception) {
        this.exception = exception;
    }

    @Override
    public void execute(Screen target) {
    }
}
