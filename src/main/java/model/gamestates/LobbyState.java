package model.gamestates;

import model.Game;
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
            throw new IllegalArgumentException("Player registration failed");
        }
    }


    public boolean unregisterPlayer(String name) {
        if(game.removePlayer(name)){
            game.notifyObservers();
            return true;
        } else {
            throw new IllegalArgumentException("Player not exists");
        }
    }

    @Override
    public boolean readyToStart() {
        if(game.getPlayers().size() == game.getMaxPlayers()) {
            game.generatePickAndPlaceStates(); //Generate PickGod and PlaceBuilder state
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

    @Override
    public boolean pickGod(String godName) {
        return false;
    }

    public boolean selectCoordinate(Coordinate coordinate) {
        return false;
    }

    public boolean quitGame() {
        return false;
    }
}
