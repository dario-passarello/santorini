package network.messages;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.logging.Level;

/**
 * A common interface to define what is message. The communication between the server and the client are handled with
 * the command patter. The message class is the command exchanged trough the network.
 * The message is the only type of objects allowed to be exchanged by all the parts in the network communication
 * network. When a message reaches his destination his execute() method should be called on an Target type object
 * @param <Target> The types that could be affected by the message using the execute method()
 */
@FunctionalInterface
public interface Message<Target extends MessageTarget> extends Serializable {

    /**
     * This method is called when the message reach his destination
     * @param target The object that is modified by the message
     */
    void execute(Target target);

    /**
     * Serializes all message content to JSON
     * @return A String with the Serialized message object
     */
    default String getMessageJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * Gets the message priority level for the message
     * @return A Level object containing the priority level for the logger
     */
    default Level getLoggerLever(){
        return Level.INFO;
    }

}
