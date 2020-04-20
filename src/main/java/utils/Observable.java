package utils;


import java.util.function.Consumer;

public interface Observable<Observer extends utils.Observer> {

    void registerObserver(Observer m);
    void unregisterObserver(Observer m);
    void notifyObservers(Consumer<Observer> notifyAction);

}
