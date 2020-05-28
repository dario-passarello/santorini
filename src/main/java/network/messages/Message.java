package network.messages;

import com.google.gson.Gson;

import java.io.Serializable;

@FunctionalInterface
public interface Message<Target extends MessageTarget> extends Serializable {

    void execute(Target target);

    default String getMessageJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
