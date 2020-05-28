package network.messages;

import java.io.Serializable;

@FunctionalInterface
public interface Message<Target extends MessageTarget> extends Serializable {

    void execute(Target target);
}
