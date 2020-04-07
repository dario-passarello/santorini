package model.gamestates;

import model.Game;
import model.Player;
import model.gods.God;
import model.gods.Mortal;
import utils.Coordinate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PickGodState implements GameState {
    Game game;
    Player player;
    int order;

    public PickGodState(Game game, Player player, int order){
        this.game = game;
    }

    public void onEntry() {
        //Skip this state if this is a three mortal game
        if(game.getGodList().stream().filter(g -> g instanceof Mortal).count() == game.getMaxPlayers()){
            List<God> godList = game.getGodList();
            List<Player> playersList = game.getPlayers();
            for(int i = 0; i < game.getMaxPlayers(); i++) {
                //Assign players to "mortals" and viceversa
                godList.get(i).setPlayer(playersList.get(i));
                playersList.get(i).setGod(godList.get(i));
            }
        }
        //There is no need to notify the view
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
        return false;
    }

    public boolean pickGod(String godName) {
        Optional<God> maybeGod =
                game.getGodList().stream()
                        .filter(g -> g.getPlayer() != null && g.getName().equals(godName))
                        .findAny();
        if(maybeGod.isPresent()) { //Check if the god is available (in the list and not already chosen)
            God god = maybeGod.get();
            //Associate God and player in both ways
            god.setPlayer(this.player);
            player.setGod(god);
        }
        else {
            //TODO log wrong choice
            return false;
        }
        if(order + 1 < game.getMaxPlayers()) {
            game.setGameState(game.getPickGodState(order + 1)); //Pick next player god
        } else {
            game.setGameState(game.getPlaceBuilderState(0));
        }
        game.notifyObservers();
        return true;
    }

    public boolean selectCoordinate(Coordinate coordinate) {
        return false;
    }

    public boolean quitGame() {
        game.setGameState(game.setupState);
        game.notifyObservers();
        return true;
    }

    public Player getPlayer(){
        return player;
    }
}
