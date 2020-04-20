package controller;

import model.Game;
import model.GameModel;
import model.gods.God;
import utils.Coordinate;

import java.util.Set;

public class GameController {

    private Game model;
    private Set<God> gods;
    private Integer NumberOfPlayers;

    public void SetNumberOfPlayers(int number, String HostName){
        try {
            if(!model.configureGame(HostName, number)){
                // TODO handle (the method was called in a wrong state)
            }

        }
        catch(IllegalArgumentException exception){
                // TODO report that the number is invalid
        }

    }

    public void AddPlayer(String name){
        try {
            if (!model.registerPlayer(name)){
                //TODO handle (the method was called in a wrong state)
            }
        }
        catch(IllegalArgumentException exception){
                //TODO report that the name already exists
        }

    }

    public void RemovePlayer(String name){
        try {
            if (!model.registerPlayer(name)){
                //TODO handle (the method was called in a wrong state)
            }
        }
        catch(IllegalArgumentException exception){
                // TODO name does not exists or name is the host of the room
        }

    }

    public void Start(){
        if(!model.readyToStart()){
            // TODO there are not enough players in the room
        }
    }

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

    public void PickGod(String GodName){
        try {
            if (!model.pickGod(GodName)){
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
