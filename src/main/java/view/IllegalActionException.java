package view;

/**
 * This class defines a particular exception, called when the action cannot be executed (usually because the parameters are invalid)
 */
public class IllegalActionException extends Exception{

    /**
     * Standard Constructor
     */
    public IllegalActionException(){
        super();
    }

    /**
     * Constructor of the exception with a String attached
     * @param message The error message
     */
    public IllegalActionException(String message){
        super(message);
    }
}
