package model.gamestates;

import model.Game;
import model.Player;
import utils.Coordinate;

import java.util.List;
import java.util.logging.Logger;

public class SetupState implements GameState {
    Game game;

    public SetupState(Game game){
        this.game = game;
    }

    @Override
    public void onEntry() {

    }

    @Override
    public void onExit() {

    }

    @Override
    public void setNumberOfPlayers(int num) {
        if(num > 1 && num <= 3){
            game.setNumberOfPlayers(num);
            game.notifyObservers();
            game.setTurnState(game.lobbyState);
        }
        else {
            //TODO Log that the number is incorrect
            //Remain in this state
        }
    }

    @Override
    public boolean addPlayer(Player p) {
        //TODO
        return false;
    }

    @Override
    public boolean removePlayer(String p) {
        //TODO
        return false;
    }

    @Override
    public void submitGodList(List<String> godList) {

    }

    @Override
    public void selectCoordinate(Player player, Coordinate coordinate) {

    }

    @Override
    public void quitGame() {

    }
}
