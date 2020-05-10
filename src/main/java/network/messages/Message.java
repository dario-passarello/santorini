package network.messages;

import network.ClientHandler;

import java.io.Serializable;

@FunctionalInterface
public interface Message<T extends MessageTarget> extends Serializable {

    void execute(T target);
}
