package model.gamestates;

import model.Board;
import model.Game;
import model.GameObserver;
import model.Player;
import model.gods.God;
import utils.Coordinate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This state represents the moment where the player can choose which god to use from the list of god previously created
 */
public class GodPickState implements GameState {
    Game game;

    /**
     * The constructor of the class
     * @param game A reference to the current game
     */
    public GodPickState(Game game) {
        this.game = game;
    }

    @Override
    public boolean submitGodList(Set<String> godNamesList) {
        return false;
    }

    @Override
    public boolean pickGod(String playerName, String godName) throws IllegalArgumentException {
        /*
         *  God should be picked in the reversed order in respect of "log-in" order
         *  withoutGod contains all players without a god
         */
        List<Player> withoutGod = game.getPlayers().stream()
                .filter(p -> p.getGod() == null)
                .collect(Collectors.toList());
        Player nextPlayer = withoutGod.get(withoutGod.size() - 1);
        if(!playerName.equals(nextPlayer.getName())) {
            throw new IllegalArgumentException("It is not " + playerName + "'s turn to pick his god, or the player not exists");
        }
        //Search if the godName could be chosen in this game, and if no one already picked it
        God god = game.getGodList().stream()
                .filter(g -> g.getPlayer() == null && g.getName().equals(godName))
                .findAny()
                .orElse(null);
        if(god == null) { //Check if the god is available (in the list and not already chosen)
            throw new IllegalArgumentException("God unavailable or God name Invalid");
        }
        //Associate God and player in both directions
        god.setPlayer(nextPlayer);
        nextPlayer.setGod(god);
        withoutGod = game.getPlayers().stream() //Update players without god after assigning a god
                .filter(p -> p.getGod() == null)
                .collect(Collectors.toList());
        //If all gods but one are picked, assign the last god
        if(withoutGod.size() == 1){
            God unpickedGod = game.getGodList().stream()
                    .filter(g -> g.getPlayer() == null)
                    .findAny()
                    .orElseThrow(() -> new UnknownError("Undefined Error"));
            unpickedGod.setPlayer(withoutGod.get(0));
            withoutGod.get(0).setGod(unpickedGod);
            game.setGameState(game.placeBuilderState, new Player(game.getFirstPlayer()));
            game.notifyObservers(obs -> { //Advance to place builder state
                obs.receivePlayerList(game.getPlayers().stream().map(Player::new).collect(Collectors.toList()));
                obs.receiveAllowedSquares(game.getBoard().getFreeCoordinates());
                obs.receiveBoard(new Board(game.getBoard()));
                obs.receiveUpdateDone();
            });
        } else {
            game.setGameState(game.godPickState, new Player(withoutGod.get(withoutGod.size() - 1)));
            game.notifyObservers(obs -> { //Advance to next pick
                obs.receivePlayerList(game.getPlayers().stream().map(Player::new).collect(Collectors.toList()));
                obs.receiveUpdateDone();
            });
        }
        return true;
    }

    @Override
    public boolean selectCoordinate(String playerName, Coordinate coordinate) {
        return false;
    }

    public boolean quitGame(String playerName) {
        game.setGameState(game.endGameState, game.getFirstPlayer());
        game.notifyObservers(GameObserver::receiveUpdateDone);
        return true;
    }

    @Override
    public Game.State getStateIdentifier() {
        return Game.State.GOD_PICK;
    }
}
