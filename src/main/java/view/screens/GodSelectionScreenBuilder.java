package view.screens;

/**
 * Screen Builder for the {@link GodSelectionScreen}
 */
public class GodSelectionScreenBuilder extends ScreenBuilder {

    private String activePlayer;
    private boolean receivedGameState;

    /**
     * Initializes the builder with the proper concrete screen factory
     * @param factory factory
     *
     */
    public GodSelectionScreenBuilder(ScreenFactory factory){
        super(factory);
        receivedGameState = false;
    }

    /**
     * Sets the active player username then wakes up the waiting thread (if it is ready)
     * @param activePlayer The active player username
     */
    public synchronized void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
        notifyAll();
    }

    /**
     * Flags the screen ready to be built, because all required message from the server are received ({@link network.messages.toclient.MatchFoundMessage})
     * Then wakes up the waiting thread (if it is ready)
     */
    public synchronized void setStateReceived() {
        receivedGameState = true;
        notifyAll();
    }

    @Override
    public Screen buildScreen() {
        try{
            synchronized (this) {
                while (activePlayer == null || !receivedGameState)
                    wait();
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return screenFactory.getGodSelectionScreen(activePlayer);
    }
}
