package view.screens;

import java.util.ArrayList;
import java.util.List;

/**
 * Screen Builder for the {@link PickGodScreen}
 */
public class PickGodScreenBuilder extends ScreenBuilder {

    private String activePlayer;
    private List<String> availableGods;

    /**
     * Initializes the Builder
     * @param factory Reference to the concrete screen factory
     */
    public PickGodScreenBuilder(ScreenFactory factory) {
        super(factory);
    }

    /**
     * Sets the active player username to provide to the screen constructor then wakes up the waiting thread (if it is ready)
     * @param activePlayer The active player username
     */
    public synchronized void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
        notifyAll();
    }

    /**
     * Sets the list of avaiable gods to provide to the screen constructor then wakes up the waiting thread (if it is ready)
     * @param availableGods A list of the names of the god to show
     */
    public synchronized void setAvailableGods(List<String> availableGods) {
        this.availableGods = new ArrayList<>(availableGods);
        notifyAll();
    }

    @Override
    public Screen buildScreen() {
        try{
            synchronized (this){
                while (activePlayer == null || availableGods == null)
                    wait();
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return screenFactory.getGodPickScreen(activePlayer,availableGods);
    }
}
