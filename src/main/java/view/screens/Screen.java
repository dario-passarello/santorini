package view.screens;

import model.*;
import model.gods.God;
import network.messages.MessageTarget;
import utils.Coordinate;
import view.View;
import view.ViewManager;

import java.util.List;

/**
 * a generic screen of the view (every screen of the view must extend this class)
 */
public abstract class Screen implements View, MessageTarget {

    protected transient final ViewManager view;
    protected transient boolean activeScreen;

    /**
     * Screen constructor
     * @param view the view manager used
     */
    public Screen(ViewManager view){
        this.view = view;
        this.activeScreen = true;
    }

    /**
     * @return view attribute
     */
    public ViewManager getView() {
        return view;
    }

    /**
     * this method will be called at the opening of a screen
     */
    public abstract void onScreenOpen();

    /**
     * this method will be called at the end of a screen
     */
    public abstract void onScreenClose();

    /**
     * @return the name associated to the view attribute
     */
    public final String getThisPlayerName() {
        return view.getThisPlayerName();
    }

    /**
     * @return the number of player associated to the view attribute
     */
    public final int getNumberOfPlayers() {
        return view.getNumberOfPlayers();
    }

    public final List<String> getPlayersNames(){
        return view.getPlayersNames();
    }

    /**
     * @return true if this is the "active screen" (the screen that can actively change on the game)
     */
    public final boolean isActiveScreen(){
        return activeScreen;
    }

    /**
     * Listener called when the {@link network.messages.toclient.MatchFoundMessage} is received from the
     * serverAdapter
     * @param assignedUsername The username assigned to this client from the server
     * @param players List of all usernames of the players in the match
     */
    public synchronized void receiveMatchFound(String assignedUsername, List<String> players){

    }

    @Override
    public synchronized void receiveGameState(Game.State state, Player activePlayerName) {

    }

    @Override
    public synchronized void receivePlayerList(List<Player> list) {

    }

    @Override
    public void receiveAllowedSquares(List<Coordinate> allowedTiles) {

    }

    @Override
    public synchronized void receiveAvailableGodList(List<God> gods) {

    }

    @Override
    public synchronized void receiveTurnState(Turn.State state, Player player) {

    }

    @Override
    public synchronized void receiveActivePlayer(Player player) {

    }

    @Override
    public synchronized void receiveAllowedSquares(Builder builder, List<Coordinate> allowedTiles, boolean specialPower) {

    }

    @Override
    public synchronized void receiveBoard(Board board) {

    }

    @Override
    public synchronized void receiveBuildersPositions(List<Builder> builders) {

    }

    @Override
    public synchronized void receiveUpdateDone() {

    }


    @Override
    public final void receiveDisconnect() {
        view.closeConnection();
        view.changeActiveScreen(view.getScreenFactory().getConnectionErrorScreen());
    }
}
