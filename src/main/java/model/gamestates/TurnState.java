package model.gamestates;

import model.Game;
import model.GameObserver;
import utils.Coordinate;

import java.util.Set;

public class TurnState implements GameState {
    Game game;

    public TurnState(Game game){
        this.game = game;
    }

    public boolean submitGodList(Set<String> godList) {
        return false;
    }

    public boolean pickGod(String playerName, String godName) {
        return false;
    }

    public boolean selectCoordinate(String playerName, Coordinate coordinate) {
        return false;
    }

    public boolean quitGame(String playerName) {
        game.removePlayer(game.getPlayers().stream()
                .filter(p -> p.getName().equals(playerName))
                .findAny().orElseThrow(IllegalArgumentException::new), true);
        game.notifyObservers(GameObserver::receiveUpdateDone);
        return true;
    }

    public Game.State getStateIdentifier() {
        return Game.State.TURN;
    }

}
