package view;

/**
 * This class defines a particular exception, called when there is something wrong with the flow of the State Machine
 */
public class ProtocolViolationException extends RuntimeException {

    /**
     * Standard constructor
     */
    public ProtocolViolationException(){
        super();
    }

    /**
     * Constructor of the exception with a String attached
     * @param message The error message
     */
    public ProtocolViolationException(String message){
        super(message);
    }
}
