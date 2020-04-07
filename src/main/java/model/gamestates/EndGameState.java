package model.gamestates;

import model.Game;
import utils.Coordinate;

import java.util.Set;

public class EndGameState implements GameState {
    Game game;

    public EndGameState(Game game){
        this.game = game;
    }

    public void onEntry() {

    }

    public void onExit() {

    }

    public boolean configureGame(int num, String hostPlayerName) {
        return false;
    }

    public boolean registerPlayer(String name) {
        return false;
    }

    public boolean unregisterPlayer(String name) {
        return false;
    }

    @Override
    public boolean readyToStart() {
        return false;
    }

    public boolean submitGodList(Set<String> godList) {
        return false;
    }

    public boolean pickGod(String godName) {
        return false;
    }

    public boolean selectCoordinate(Coordinate coordinate) {
        return false;
    }

    public boolean quitGame() {
        game.setGameState(game.setupState);
        game.notifyObservers();
        return true;
    }
}
