package view;

/**
 * This class defines a particular Exception, called when the Player calls a method when it is not supposed to be its turn
 */
public class ActivityNotAllowedException extends RuntimeException{

    /**
     * Standard Constructor
     */
    public ActivityNotAllowedException(){
        super();
    }

    /**
     * Constructor of the exception with a String attached
     * @param message The error message
     */
    public ActivityNotAllowedException(String message){
        super(message);
    }
}
