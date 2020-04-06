package model.gamestates;

import utils.Coordinate;

import java.util.Set;

/**
 *  Interface for the States of the GameState machine
 */
public interface GameState {
    /**
     *  Executed when the state machine enters in the state
     */
    void onEntry();
    /**
     *  Executed when the state machine exits the state
     */
    void onExit();


    boolean configureGame(int num, String hostPlayerName);

    boolean registerPlayer(String name);

    boolean unregisterPlayer(String name);

    boolean readyToStart();

    boolean submitGodList(Set<String> godList);

    boolean pickGod(String godName);

    boolean selectCoordinate(Coordinate coordinate);

    boolean quitGame();

}
