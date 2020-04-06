package model.gamestates;

import model.Game;
import model.gods.God;
import model.gods.GodFactory;
import utils.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GodSelectionState implements GameState {
    Game game;

    public GodSelectionState(Game game){
        this.game = game;
    }

    public void onEntry() {

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

    public boolean submitGodList(Set<String> godNamesList) {
        List<God> godObjectsList = new ArrayList<>();
        GodFactory factory = new GodFactory();
        if(godNamesList.isEmpty()){ //No gods provided => A 3 Mortals game will be started
            for(int i = 0; i < game.getMaxPlayers(); i++){
                godObjectsList.add(factory.getGod("Mortal"));
            }
        } else if(godNamesList.size() == game.getMaxPlayers()) { //Ma
            godObjectsList = godNamesList.stream().map(factory::getGod).collect(Collectors.toList());
        } else {
            //TODO Log error
            return false;
        }
        for(God g : godObjectsList) {    //Apply global win condition to all gods
            g.configureAllOtherWinConditions(godObjectsList);
        }
        godObjectsList.forEach(God::captureResetBehaviors); //Set reset methods
        game.setGodList(godObjectsList);
        game.setGameState(game.getPickGodState(0));
        game.notifyObservers();
        return true;
    }


    public boolean pickGod(String godName) {
        return false;
    }

    public boolean selectCoordinate(Coordinate coordinate) {
        return false;
    }

    public boolean quitGame() {
        game.setGameState(game.setupState);
        game.notifyObservers();
        return true;
    }
}
