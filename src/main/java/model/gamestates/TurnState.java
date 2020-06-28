package model.gamestates;

import model.Game;
import model.GameObserver;
import utils.Coordinate;

import java.util.Set;

/**
 * This class represents the Turn State. When this state is reached, the game starts
 */
public class TurnState implements GameState {
    Game game;

    /**
     * The constructor of the class
     * @param game A reference to the current game
     */
    public TurnState(Game game){
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
        game.removePlayer(game.getPlayers().stream()
                .filter(p -> p.getName().equals(playerName))
                .findAny().orElseThrow(IllegalArgumentException::new), true);
        game.notifyObservers(GameObserver::receiveUpdateDone);
        return true;
    }

    @Override
    public Game.State getStateIdentifier() {
        return Game.State.TURN;
    }

}
