package view;

import model.GameObserver;
import model.TurnObserver;

/**
 * A view is any views that observe the game and send commands to the
 * controller. The view observes the Turn and the Game
 */
public interface View extends TurnObserver, GameObserver {

}
