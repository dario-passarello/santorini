package model.gamestates;

import model.Game;
import model.GameObserver;
import model.Player;
import model.gods.God;
import model.gods.GodFactory;
import utils.Coordinate;

import java.util.*;
import java.util.stream.Collectors;

public class GodSelectionState implements GameState {
    Game game;

    public GodSelectionState(Game game){
        this.game = game;
    }

    public boolean submitGodList(Set<String> godNamesList) {
        List<God> godObjectsList = new ArrayList<>();
        GodFactory factory = new GodFactory();
        GameState nextState;
        Player nextPlayer; //The player who will do the next action
        if(godNamesList.isEmpty()) { //No gods provided => A 3 Mortals game will be started
            for(int i = 0; i < game.getNumberOfPlayers(); i++){
                godObjectsList.add(factory.getGod("Mortal"));
            }
            for(int i = 0; i < game.getNumberOfPlayers(); i++) {
                //Bind players to "mortals" and "mortals" to players
                godObjectsList.get(i).setPlayer(game.getPlayers().get(i));
                game.getPlayers().get(i).setGod(godObjectsList.get(i));
            }
            nextState = game.placeBuilderState;
            nextPlayer = game.getFirstPlayer();
        } else if(godNamesList.size() == game.getNumberOfPlayers()) { //Ma
            godObjectsList = godNamesList.stream().map(factory::getGod).collect(Collectors.toList());
            nextState = game.godPickState;
            nextPlayer = game.getLastPlayer();
        } else {
            throw new IllegalArgumentException("Malformed God List");
        }
        for(God g : godObjectsList) {    //Apply global win condition to all gods
            g.configureAllOtherWinConditions(godObjectsList);
        }
        godObjectsList.forEach(God::captureResetBehaviors); //Set reset methods
        game.setGodList(godObjectsList);
        game.setGameState(nextState, nextPlayer);
        game.notifyObservers((obs) -> {
            obs.receiveAvailableGodList(game.getGodList().stream().map(God::new).collect(Collectors.toList()));
            obs.receivePlayerList(game.getPlayers().stream().map(Player::new).collect(Collectors.toList()));
            obs.receiveUpdateDone();
        });
        return true;
    }

    public boolean pickGod(String player, String godName) {
        return false;
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
        return Game.State.GOD_SELECTION;
    }
}
