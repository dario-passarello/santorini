package model;

import utils.Observer;
import java.util.List;


public interface GameObserver extends Observer {

    void receiveGameState(Game.State state);

    void notifyPlayerElimination(String playerName);

}
