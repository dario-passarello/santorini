package model.gamestates;

import model.Game;
import model.Player;
import model.gods.GodFactory;
import utils.Coordinate;

import java.util.Set;
import java.util.stream.Collectors;

public class PickGodState implements GameState {
    Game game;

    public PickGodState(Game game){
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

    public boolean readyToStart() {
        return false;
    }

    public boolean submitGodList(Set<String> godList) {
        if(godList.isEmpty() || godList)
        GodFactory fact = new GodFactory();
        game.setGodList(godList.stream().map(fact::getGod).collect(Collectors.toList()));
        game.setGameState(game.pickGodState);
        game.notifyObservers();
        return true;
    }

    public boolean selectCoordinate(Player player, Coordinate coordinate) {
        return false;
    }

    public boolean quitGame() {
        game.setGameState(game.setupState);
        game.notifyObservers();
        return true;
    }
}
