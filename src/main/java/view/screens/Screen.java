package view.screens;

import model.*;
import model.gods.God;
import network.messages.MessageTarget;
import utils.Coordinate;
import view.View;
import view.ViewManager;

import java.util.List;

public abstract class Screen implements View, MessageTarget {

    protected final ViewManager view;
    protected boolean activeScreen;

    public Screen(ViewManager view){
        this.view = view;
        this.activeScreen = true;
    }

    public ViewManager getView() {
        return view;
    }

    public abstract void onScreenOpen();

    public abstract void onScreenClose();

    public final String getThisPlayerName() {
        return view.getThisPlayerName();
    }

    public final int getNumberOfPlayers() {
        return view.getNumberOfPlayers();
    }

    public final List<String> getPlayersNames(){
        return view.getPlayersNames();
    }

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
    public synchronized void receivePlayerOutcome(Player playerName, boolean winner) {

    }

    @Override
    public synchronized void receivePlayerList(List<Player> list) {

    }

    @Override
    public synchronized void receiveAvailableGodList(List<God> gods) {

    }

    @Override
    public synchronized void receiveStateChange(Turn.State state) {

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
}
