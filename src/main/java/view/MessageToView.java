package view;

public interface MessageToView {
    /**
     * Executes the command of a message to the target
     * @param target The target of the message
     */
    void execute(ClientView target);
}
