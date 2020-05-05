package model.gamestates;

import model.Board;
import model.Game;
import model.GameObserver;
import model.Player;
import model.gods.God;
import utils.Coordinate;

import java.util.*;
import java.util.stream.Collectors;

public class GodPickState implements GameState {
    Game game;

    public GodPickState(Game game) {
        this.game = game;
    }

    public boolean submitGodList(Set<String> godList) {
        return false;
    }

    public boolean pickGod(String playerName, String godName) {
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
            throw new IllegalArgumentException("God Name Invalid");
        }
        //Associate God and player in both directions
        god.setPlayer(nextPlayer);
        nextPlayer.setGod(god);
        //If all gods but one are picked, assign the last god
        if(withoutGod.size() == 2){
            God unpickedGod = game.getGodList().stream()
                    .filter(g -> g.getPlayer() == null)
                    .findAny()
                    .orElseThrow(() -> new UnknownError("Undefined Error"));
            unpickedGod.setPlayer(withoutGod.get(0));
            withoutGod.get(0).setGod(unpickedGod);
            game.setGameState(game.placeBuilderState, new Player(game.getFirstPlayer()));
            game.notifyObservers(obs -> {
                obs.receivePlayerList(game.getPlayers().stream().map(Player::new).collect(Collectors.toList()));
                obs.receiveUpdateDone();
            });
        } else {
            game.setGameState(game.godPickState, new Player(withoutGod.get(withoutGod.size() - 2)));
            game.notifyObservers(obs -> {
                obs.receivePlayerList(game.getPlayers().stream().map(Player::new).collect(Collectors.toList()));
                obs.receiveBoard(new Board(game.getBoard()));
                obs.receiveUpdateDone();
            });
        }
        return true;
    }

    public boolean selectCoordinate(String playerName, Coordinate coordinate) {
        return false;
    }

    public boolean quitGame() {
        game.setGameState(game.endGameState, null);
        game.notifyObservers(GameObserver::receiveUpdateDone);
        return true;
    }

    public Game.State getStateIdentifier() {
        return Game.State.GOD_PICK;
    }
}
