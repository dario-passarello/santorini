package model.gamestates;

import model.*;
import utils.Coordinate;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
        Supplier<Player> nextPlayerCalculator = () -> game.getPlayersTurnOrder().stream()
                .filter(p -> p.getBuilders().size() < Player.BUILDERS_PER_PLAYER)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("All builders are placed"));
        Consumer<GameObserver> updateAction;
        boolean nextTurn;
        Player nextPlayer = nextPlayerCalculator.get();
        if(!nextPlayer.getName().equals(playerName)) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_BUILD_OWNER+ "\n"
                    + "Actual: " + playerName + " Expected: " + nextPlayer.getName());
        }
        if(!Board.checkValidCoordinate(coordinate)) {
            throw new IllegalArgumentException(ErrorMessage.COORDINATE_NOT_VALID);
        }
        if(!game.getBoard().getFreeSquares().contains(game.getBoard().squareAt(coordinate))){
            throw new IllegalArgumentException("Square " + coordinate.toString() +  " is not free"); //ADD MESSAGE
        }
        nextPlayer.addBuilder(game.getBoard().squareAt(coordinate));
        nextTurn = game.getPlayers().stream().noneMatch(p -> p.getBuilders().size() < Player.BUILDERS_PER_PLAYER);
        if(nextTurn) {
            game.setGameState(game.turnState, new Player(game.getFirstPlayer()));
            updateAction = obs -> {
                obs.receivePlayerList(game.getPlayers().stream().map(Player::new).collect(Collectors.toList()));
                obs.receiveBuildersPositions(game.getPlayers().stream()
                        .flatMap(b -> b.getBuilders().stream()).collect(Collectors.toList()));
                obs.receiveUpdateDone();
            };
        }
        else {
            nextPlayer = nextPlayerCalculator.get();
            game.setGameState(game.placeBuilderState, new Player(nextPlayer));
            updateAction = obs -> {
                obs.receivePlayerList(game.getPlayers().stream().map(Player::new).collect(Collectors.toList()));
                obs.receiveBuildersPositions(game.getPlayers().stream()
                        .flatMap(b -> b.getBuilders().stream()).collect(Collectors.toList()));
                obs.receiveAllowedSquares(game.getBoard().getFreeCoordinates());
                obs.receiveBoard(new Board(game.getBoard()));
                obs.receiveUpdateDone();
            };
        }
        if(nextTurn)
            game.nextTurn(true);
        game.notifyObservers(updateAction);
        return true;
    }

    public boolean quitGame(String playerName) {
        game.setGameState(game.endGameState, game.getFirstPlayer());
        game.notifyObservers(GameObserver::receiveUpdateDone);
        return true;
    }

    public Game.State getStateIdentifier() {
        return Game.State.PLACE_BUILDER;
    }

}
