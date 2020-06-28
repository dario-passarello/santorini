package model.gamestates;

import model.Game;
import utils.Coordinate;

import java.util.Set;

/**
 * This state represents the End of the game. When this game is reached, nothing can be called
 */
public class EndGameState implements GameState {
    Game game;

    /**
     * The constructor of the class
     * @param game A reference to the current game
     */
    public EndGameState(Game game){
        this.game = game;
    }

    @Override
    public boolean submitGodList(Set<String> godNamesList) {
        return false;
    }

    @Override
    public boolean pickGod(String playerName, String godName) {
        return false;
    }

    @Override
    public boolean selectCoordinate(String playerName, Coordinate coordinate) {
        return false;
    }

    @Override
    public boolean quitGame(String playerName) {
        return false;
    }

    @Override
    public Game.State getStateIdentifier() {
        return Game.State.END_GAME;
    }
}
