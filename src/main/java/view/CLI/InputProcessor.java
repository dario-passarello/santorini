package view.CLI;

/**
 * This interface accomunates every screen that interacts with an InputListener
 */
public interface InputProcessor {


    /**
     * This method is the adapter between the InputListener and the Input Handler of the screen
     * @param input
     */
    void processInput(String input);

}
