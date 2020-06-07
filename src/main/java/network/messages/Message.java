package network.messages;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.logging.Level;

@FunctionalInterface
public interface Message<Target extends MessageTarget> extends Serializable {

    void execute(Target target);

    default String getMessageJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    default Level getLoggerLever(){
        return Level.INFO;
    }

}
