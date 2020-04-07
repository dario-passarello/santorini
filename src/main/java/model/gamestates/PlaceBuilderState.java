package model.gamestates;

import model.Board;
import model.Game;
import model.Player;
import utils.Coordinate;

import java.util.Set;

public class PlaceBuilderState implements GameState {
    private final Player player;
    private final Game game;
    private final int order;
    private int buildersPlaced;

    public PlaceBuilderState(Game game, Player player, int order){
        this.game = game;
        this.player = player;
        this.order = order;
        this.buildersPlaced = 0;
    }

    public void onEntry() {
        this.buildersPlaced = 0;
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
        return false;
    }

    public boolean selectCoordinate(Coordinate coordinate) {
        if(!Board.checkValidCoordinate(coordinate)) {
            return false; //The square should be valid
        }
        if(!game.getBoard().getFreeSquares().contains(game.getBoard().squareAt(coordinate))){
            return false; //The square should be empty
        }
        player.addBuilder(game.getBoard().squareAt(coordinate)); //Add new builder
        buildersPlaced++;
        if(buildersPlaced >= Game.BUILDERS_PER_PLAYER) { //If all builder were placed
            if(order + 1 < game.getMaxPlayers()) { //If other players need to place builder
                game.setGameState(game.getPlaceBuilderState(order + 1));
            }
            else{
                game.setGameState(game.turnState);
            }
        }
        game.notifyObservers();
        return true;
    }

    public boolean quitGame() {
        game.setGameState(game.setupState);
        game.notifyObservers();
        return true;
    }

    public Player getPlayer() {
        return player;
    }
}
