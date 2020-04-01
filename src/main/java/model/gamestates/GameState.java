package model.gamestates;

import model.Player;
import model.gods.God;
import utils.Coordinate;

import java.util.List;

public interface GameState {

    void onEntry();
    void onExit();

    void setNumberOfPlayers();
    boolean addPlayer(Player p);
    boolean removePlayer(Player p);
    void submitGodList(List<God> godList);
    void selectCoordinate(Player p, Coordinate coord);
    void quitGame();

}
