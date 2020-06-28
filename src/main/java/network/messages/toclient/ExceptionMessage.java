package network.messages.toclient;

import network.messages.Message;
import view.screens.Screen;

/**
 * A message sent to the client when an illegal call to controller is made
 */
public class ExceptionMessage implements Message<Screen> {
    private Exception exception;

    /**
     * Creates an ExceptionMessage object
     * @param exception The exception raised from the controller
     */
    public ExceptionMessage(Exception exception) {
        this.exception = exception;
    }

    @Override
    public void execute(Screen target) {
    }
}
