package view.screens;

import model.Player;
import utils.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class BoardScreenBuilder extends ScreenBuilder {

    private String activePlayer;
    private List<Player> playerList;
    private List<Coordinate> preHighlightedCoordinates;

    public BoardScreenBuilder(ScreenFactory factory) {
        super(factory);
    }


    public synchronized void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
    }

    public synchronized void setPlayerList(List<Player> playersList) {
        this.playerList = new ArrayList<>(playersList);
    }

    public synchronized void setPreHighlightedCoordinates(List<Coordinate> preHighlightedCoordinates){
        this.preHighlightedCoordinates = new ArrayList<>(preHighlightedCoordinates);
    }

    @Override
    public Screen buildScreen() {
        try{
            synchronized (this){
                while (activePlayer == null || playerList == null || preHighlightedCoordinates == null)
                    wait();
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return screenFactory.getBoardScreen(activePlayer,playerList,preHighlightedCoordinates);
    }
}
