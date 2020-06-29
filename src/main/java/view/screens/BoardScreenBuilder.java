package view.screens;

import model.Player;
import utils.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for the {@link BoardScreen}
 */
public class BoardScreenBuilder extends ScreenBuilder {

    private String activePlayer;
    private List<Player> playerList;
    private List<Coordinate> preHighlightedCoordinates;

    /**
     * Initializes the screen builder with the proper screen factory
     * @param factory The factory that would build the screen once all parameters are received
     */
    public BoardScreenBuilder(ScreenFactory factory) {
        super(factory);
    }

    /**
     * Sets the active player, then wakes up the waiting buildScreen() thread
     * @param activePlayer The username of the first active player in the board screen
     */
    public synchronized void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
        notifyAll();
    }

    /**
     * Sets the list of the players, then wakes up the waiting buildScreen() thread
     * @param playersList A list of all players object representing all players in game
     */
    public synchronized void setPlayerList(List<Player> playersList) {
        this.playerList = new ArrayList<>(playersList);
        notifyAll();
    }

    /**
     * Sets the coordinates to highlight when the screen is displayed, then wakes up the waiting buildScreen() thread
     * @param preHighlightedCoordinates A list of pre highlighted coordinates
     */
    public synchronized void setPreHighlightedCoordinates(List<Coordinate> preHighlightedCoordinates){
        this.preHighlightedCoordinates = new ArrayList<>(preHighlightedCoordinates);
        notifyAll();
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
