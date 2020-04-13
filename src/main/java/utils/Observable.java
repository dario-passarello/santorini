package utils;

import model.Square;

import java.util.HashSet;
import java.util.Set;

public interface Observable {
    void addObserver(Observer o);
    void deleteObserver(Observer o);

    /**
     * Empty notify. Could be used for general purposes
     */
    void notifyObservers();

    /**
     * Notify to transmit a Set of Squares to the observer
     * @param set
     */
    void notifyObservers(Set<Square> set);

    /**
     * Notify to transmit a boolean to the observer
     * @param condition
     */
    void notifyObservers(boolean condition);


    /**
     * Notify to transmit both a Set of Squares and a Boolean to the Observer
     * @param set
     * @param condition
     */
    void notifyObservers(Set<Square> set, boolean condition);





}
