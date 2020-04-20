package model.gamestates;

import model.Game;
import utils.Coordinate;

import java.util.Set;

/**
 *  Interface for the States of the GameState machine
 */
public interface GameState {
    boolean submitGodList(Set<String> godList);

    boolean pickGod(String playerName, String godName);

    boolean selectCoordinate(String playerName, Coordinate coordinate);

    boolean quitGame();

    Game.State getStateIdentifier();

}
