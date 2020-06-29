package view;

/**
 *  IllegalValueException is a checked exception thrown when an user input is not valid
 */
public class IllegalValueException extends Exception {

    /**
     * Standard constructor
     */
    public IllegalValueException(){
        super();
    }

    /**
     * Constructor of the exception with a String attached
     * @param message The error message
     */
    public IllegalValueException(String message){
        super(message);
    }

    
}
