package model.gamestates;

import model.Game;
import utils.Coordinate;

import java.util.Set;

/**
 *  Interface for the States of the GameState machine
 */
public interface GameState {
    boolean submitGodList(Set<String> godList) throws IllegalArgumentException;

    boolean pickGod(String playerName, String godName) throws IllegalArgumentException;

    boolean selectCoordinate(String playerName, Coordinate coordinate) throws IllegalArgumentException;

    boolean quitGame(String playerName) throws IllegalArgumentException;

    Game.State getStateIdentifier();

}
