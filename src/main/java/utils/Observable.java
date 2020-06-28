package utils;


import java.util.function.Consumer;

/**
 * This interface is a custom implementation of the Observer pattern
 * It is a parametric implementation
 * @param <Observer>
 */
public interface Observable<Observer extends utils.Observer> {

    /**
     * Registers the parameter to the list of observers
     * @param m A class that implements the Observer interface
     */
    void registerObserver(Observer m);

    /**
     * Unregisters the parameter from the list of observers
     * @param m A class that implements the Observer Interface
     */
    void unregisterObserver(Observer m);

    /**
     * This method notifies a specific class of observers with the action the observable class wants them to make
     * @param notifyAction The action to be notified
     */
    void notifyObservers(Consumer<Observer> notifyAction);

}
