package controller;

import model.DuplicateNameException;
import model.Game;
import model.gods.God;
import network.RemoteView;
import utils.Coordinate;

import java.util.List;
import java.util.Set;

public class GameController {


    private Game model;
    private Set<God> gods;
    private List<RemoteView> remoteviews;
    private Integer NumberOfPlayers;

    public GameController(List<RemoteView> remoteviews, List<String> nicknames) throws DuplicateNameException {
        this.model = new Game(nicknames, nicknames.size());
        this.remoteviews = remoteviews;
        this.NumberOfPlayers = nicknames.size();
    }

    public void StartGame(){
        model.setGameState(model.godSelectionState);
        // Maybe Send GodList?
    }



    // TODO Handle removal of the player


    public void submitGodList(Set<String> GodList){
        try {
            if (!model.submitGodList(GodList)){
                //TODO handle (the method was called in a wrong state)
            }
        }
        catch(IllegalArgumentException exception){
            // TODO the list of Gods is incorrect
        }

    }

    public void Reset() {
        if (!model.quitGame()) {
            //TODO handle (the method was called in a wrong state)
        }
    }

    public void PickGod(String PlayerName, String GodName){
        try {
            if (!model.pickGod(PlayerName, GodName)){
                //TODO handle (the method was called in a wrong state)
            }
        }
        catch(IllegalArgumentException exception){
            // TODO the selected God is not correct
        }
    }

    public void SelectCoordinate(Coordinate coordinate){

    }

    public void quitGame(){

    }

}
