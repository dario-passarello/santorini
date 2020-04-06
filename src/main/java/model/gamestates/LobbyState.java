package model.gamestates;

import model.Game;
import model.Player;
import utils.Coordinate;

import java.util.Set;

public class LobbyState implements GameState {
    Game game;
    public LobbyState(Game game){
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
        if(game.createPlayer(name)){
            game.notifyObservers();
            return true;
        } else {
            return false;
        }
    }


    public boolean unregisterPlayer(String name) {
        if(game.removePlayer(name)){
            game.notifyObservers();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean readyToStart() {
        if(game.getPlayers().size() == game.getMaxPlayers()) {
            game.setGameState(game.godSelectionState);
            game.notifyObservers();
            return true;
        } else {
            return false;
        }
    }


    public boolean submitGodList(Set<String> godList) {
        return false;
    }

    public boolean selectCoordinate(Player player, Coordinate coordinate) {
        return false;
    }

    public boolean quitGame() {
        return false;
    }
}
