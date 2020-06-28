package model.gamestates;

import model.Game;
import model.GameObserver;
import model.Player;
import model.gods.God;
import model.gods.GodFactory;
import utils.Coordinate;
import utils.Observer;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * This State represents the moment where the player can selects the list of gods he wants to be available in the game
 */
public class GodSelectionState implements GameState {
    Game game;

    /**
     * The constructor of the class
     * @param game A reference to the current game
     */
    public GodSelectionState(Game game){
        this.game = game;
    }

    @Override
    public boolean submitGodList(Set<String> godNamesList) throws IllegalArgumentException {
        List<God> godObjectsList = new ArrayList<>();
        Consumer<GameObserver> updateAction;
        GodFactory factory = new GodFactory();
        GameState nextState;
        Player nextPlayer; //The player who will do the next action
        if(godNamesList.isEmpty() || (godNamesList.size() == 1 && godNamesList.contains("Mortal"))) {
            //No gods provided => A 3 Mortals game will be started
            for(int i = 0; i < game.getNumberOfPlayers(); i++){
                godObjectsList.add(factory.getGod("Mortal"));
            }
            for(int i = 0; i < game.getNumberOfPlayers(); i++) {
                //Bind players to "mortals" and "mortals" to players
                godObjectsList.get(i).setPlayer(game.getPlayers().get(i));
                game.getPlayers().get(i).setGod(godObjectsList.get(i));
            }
            nextState = game.placeBuilderState;
            updateAction = obs -> {
                obs.receivePlayerList(game.getPlayers().stream().map(Player::new).collect(Collectors.toList()));
                obs.receiveBoard(game.getBoard());
                obs.receiveAllowedSquares(game.getBoard().getFreeCoordinates());
                obs.receiveUpdateDone();
            };
            nextPlayer = game.getFirstPlayer();
        } else if(godNamesList.size() == game.getNumberOfPlayers()) { //Ma
            godObjectsList = godNamesList.stream().map(factory::getGod).collect(Collectors.toList());
            nextState = game.godPickState;
            updateAction = (obs) -> {
                obs.receiveAvailableGodList(game.getGodList().stream().map(God::new).collect(Collectors.toList()));
                obs.receivePlayerList(game.getPlayers().stream().map(Player::new).collect(Collectors.toList()));
                obs.receiveUpdateDone();
            };
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
        game.notifyObservers(updateAction);
        return true;
    }

    @Override
    public boolean pickGod(String playerName, String godName) {
        return false;
    }

    @Override
    public boolean selectCoordinate(String playerName, Coordinate coordinate) {
        return false;
    }

    @Override
    public boolean quitGame(String playerName) {
        game.setGameState(game.endGameState, game.getFirstPlayer());
        game.notifyObservers(GameObserver::receiveUpdateDone);
        return true;
    }

    @Override
    public Game.State getStateIdentifier() {
        return Game.State.GOD_SELECTION;
    }
}
