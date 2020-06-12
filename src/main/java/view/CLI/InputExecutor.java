package view.CLI;

/**
 * This is the interface that defines every class that represent a different phase of the game
 */
public interface InputExecutor {

    /**
     * The message that defines the specific phase
     */
    default void message() {

    }

    /**
     * Handles the input from stdin according to the current phase
     * @param s The input from stdin
     */
    void execute(String s);


}
