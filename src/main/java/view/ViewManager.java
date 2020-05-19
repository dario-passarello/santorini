package view;

import model.*;
import model.gods.God;

import network.ServerAdapter;
import network.messages.Message;
import network.messages.MessageTarget;
import utils.Coordinate;

import java.util.List;
/**
 * Defines a common interface for all Client-Side Views
 * Client side Views are the targets of all message sent from
 * the server using the @link{MessageToView} interface
 */
public class ViewManager implements View, MessageTarget{

    private ServerAdapter serverAdapter;
    protected final ScreenFactory screenFactory;
    protected Screen activeScreen;

    public ViewManager(ScreenFactory screenFactory) {
        this.screenFactory = screenFactory;
    }


    public void openConnection(String ip, int port){
        if(serverAdapter != null) {
            throw new IllegalStateException(ClientErrorMessages.CONNECTION_ALREADY_OPEN);
        }
        serverAdapter = new ServerAdapter(this,ip,port);
        Thread adapterThread = new Thread(serverAdapter);
        adapterThread.start();
    }

    public void closeConnection() {
        if(serverAdapter == null) {
            throw new IllegalStateException(ClientErrorMessages.CONNECTION_CLOSED);
        }
        serverAdapter.closeConnection();
        serverAdapter = null;
    }

    public void sendMessage(Message<? extends MessageTarget> messageToServer){
        if(serverAdapter == null) {
            throw new IllegalStateException(ClientErrorMessages.CONNECTION_CLOSED);
        }
        serverAdapter.sendMessage(messageToServer);
    }


    @Override
    public void receiveGameState(Game.State state, Player activePlayerName) {
        activeScreen.receiveGameState(state,activePlayerName);
    }

    @Override
    public void receivePlayerOutcome(Player playerName, boolean winner) {
        activeScreen.receivePlayerOutcome(playerName,winner);
    }

    @Override
    public void receivePlayerList(List<Player> map) {
        activeScreen.receivePlayerList(map);
    }

    @Override
    public void receiveAvailableGodList(List<God> gods) {
        activeScreen.receiveAvailableGodList(gods);
    }

    @Override
    public void receiveStateChange(Turn.State state) {
        activeScreen.receiveStateChange(state);
    }

    @Override
    public void receiveActivePlayer(Player player) {
        activeScreen.receiveActivePlayer(player);
    }

    @Override
    public void receiveAllowedSquares(Builder builder, List<Coordinate> allowedTiles, boolean specialPower) {
        activeScreen.receiveAllowedSquares(builder,allowedTiles,specialPower);
    }

    @Override
    public void receiveBoard(Board board) {
        activeScreen.receiveBoard(board);
    }

    @Override
    public void receiveBuildersPositions(List<Builder> builders) {
        activeScreen.receiveBuildersPositions(builders);
    }

    @Override
    public void receiveUpdateDone() {
        activeScreen.receiveUpdateDone();
    }

}
