package model.gamestates;

import model.Game;
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

    public boolean pickGod(String playerName, String godName) {
        /*
         *  God should be picked in the reversed order in respect of "log-in" order
         *  withoutGodReversed contains all players without a god in the "pick-order"
         */
        List<Player> withoutGodReversed = game.getPlayers().stream()
                .filter(p -> p.getGod() == null)
                .collect(Collectors.toList());
        Collections.reverse(withoutGodReversed);
        Player nextPlayer = withoutGodReversed.stream() //Next player that should pick
                .findFirst()
                .orElse(null);
        if(nextPlayer == null) {
            throw new IllegalStateException("All player already picked a god");
        }
        else if(!playerName.equals(nextPlayer.getName())) {
            throw new IllegalArgumentException("It is not " + playerName + "'s turn to pick his god, or the player not exists");
        }
        //Search if the godName could be chosen in this game, and if no one already picked it
        God god = game.getGodList().stream()
                .filter(g -> g.getPlayer() != null && g.getName().equals(godName))
                .findAny()
                .orElse(null);
        if(god == null) { //Check if the god is available (in the list and not already chosen)
            throw new IllegalArgumentException("God Name Invalid");
        }
        //Associate God and player in both directions
        god.setPlayer(nextPlayer);
        nextPlayer.setGod(god);
        //If all gods but one are picked, assign the last god
        if(withoutGodReversed.size() == 2){
            God unpickedGod = game.getGodList().stream()
                    .filter(g -> g.getPlayer() == null)
                    .findAny()
                    .orElseThrow(() -> new UnknownError("Undefined Error"));
            unpickedGod.setPlayer(withoutGodReversed.get(0));
            withoutGodReversed.get(0).setGod(unpickedGod);
            game.setGameState(game.placeBuilderState);
        } else {
            game.setGameState(game.godPickState);
        }
        return true;
    }

    public boolean selectCoordinate(String playerName, Coordinate coordinate) {
        return false;
    }

    public boolean quitGame() {
        game.setGameState(game.endGameState);
        return true;
    }

    public Game.State getStateIdentifier() {
        return Game.State.GOD_PICK;
    }
}
