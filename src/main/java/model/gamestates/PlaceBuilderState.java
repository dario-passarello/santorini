package model.gamestates;

import model.Board;
import model.ErrorMessage;
import model.Game;
import model.Player;
import utils.Coordinate;

import java.util.Set;

public class PlaceBuilderState implements GameState {
    private final Game game;

    public PlaceBuilderState(Game game){
        this.game = game;
    }

    public boolean submitGodList(Set<String> godList) {
        return false;
    }

    public boolean pickGod(String playerName, String godName) {
        return false;
    }

    public boolean selectCoordinate(String playerName, Coordinate coordinate) {
        Player nextPlayer = game.getPlayers().stream()
                .filter(p -> p.getBuilders().size() < Player.BUILDERS_PER_PLAYER)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("All builders are placed"));
        if(!nextPlayer.getName().equals(playerName)) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_BUILD_OWNER);
        }
        if(!Board.checkValidCoordinate(coordinate)) {
            throw new IllegalArgumentException("Coordinate is outside the board");
        }
        if(!game.getBoard().getFreeSquares().contains(game.getBoard().squareAt(coordinate))){
            throw new IllegalArgumentException("Square is not free");
        }
        nextPlayer.addBuilder(game.getBoard().squareAt(coordinate));
        if(game.getPlayers().stream().noneMatch(p -> p.getBuilders().size() < Player.BUILDERS_PER_PLAYER)) {
            game.setGameState(game.turnState);
            game.nextTurn(true);
        }
        else {
            game.setGameState(game.placeBuilderState);
        }
        return true;
    }

    public boolean quitGame() {
        game.setGameState(game.endGameState);
        return true;
    }

    public Game.State getStateIdentifier() {
        return Game.State.PLACE_BUILDER;
    }

}
