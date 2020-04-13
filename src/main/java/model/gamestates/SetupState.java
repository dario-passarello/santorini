package model.gamestates;

import model.Game;
import utils.Coordinate;

import java.util.Set;

public class SetupState implements GameState {
    Game game;

    public SetupState(Game game){
        this.game = game;
    }

    public void onEntry() {

    }

    public void onExit() {

    }

    public boolean configureGame(int num, String hostPlayerName) {
        if(num > 1 && num <= 3 && !hostPlayerName.isEmpty()){
            game.setMaxPlayers(num);
            game.createPlayer(hostPlayerName);
            game.setGameState(game.lobbyState);
            game.notifyObservers();
            return true;
        }
        else {
            //TODO Log that the number is incorrect
            throw new IllegalArgumentException("Player numbers allowed are 2 or 3");
        }
    }
    //TODO: Add proper logger
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
