package view.screens;

/**
 * A screen builder saves the initial parameters for the next screen, and then when the
 * buildScreen() method is called builds and returns the next screen object, using the provided
 * {@link ScreenFactory}
 */
public abstract class ScreenBuilder {
    /**
     * The screen factory that would return the correct screen (GUI or CLI)
     */
    protected ScreenFactory screenFactory;

    /**
     * Initializes the screen builder with the proper screen factory
     * @param factory The factory that would build the screen once all parameters are received
     */
    public ScreenBuilder(ScreenFactory factory){
        this.screenFactory = factory;
    }

    /**
     * Builds the screen once all parameters are received
     * NOTE: Some implementations could suspend the thread if not all parameters are received
     * @return The screen requested
     */
    public abstract Screen buildScreen();
}
