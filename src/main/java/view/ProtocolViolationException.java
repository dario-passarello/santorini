package view;

public class ProtocolViolationException extends RuntimeException {
    public ProtocolViolationException(){
        super();
    }

    public ProtocolViolationException(String message){
        super(message);
    }
}
