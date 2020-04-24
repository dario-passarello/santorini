package utils;

import java.io.Serializable;

public class Message implements Serializable {

    private final Object data;
    private final MessageType type;

    public Message(Object data, MessageType type){
        this.data = data;
        this.type = type;
    }

    /**
     *
     * @return the content of the message
     */
    public Object getContent(){
        return this.data;
    }

    /**
     *
     * @return the type of the message contained inside this class
     */
    public MessageType getMessageType(){
        return this.type;
    }

}
