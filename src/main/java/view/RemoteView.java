package view;

import controller.Controller;
import model.*;
import model.gods.God;
import network.ClientHandler;
import network.messages.Message;
import network.messages.MessageTarget;
import network.messages.toclient.*;
import utils.Coordinate;

import java.util.List;

public class RemoteView implements GameObserver, TurnObserver, MessageTarget {

    private String playerName;
    private final Controller controller;
    private final ClientHandler client;
    private Game.State lastGameState;

    public RemoteView(ClientHandler client, Controller controller, String playerName) {
        this.controller = controller;
        this.playerName = playerName;
        this.client = client;
    }

    public void sendMessage(Message<? extends MessageTarget>message) {
        if(client != null) {
            client.sendMessage(message);
        }
    }

    public Controller getController() {
        return controller;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public void receiveGameState(Game.State state, Player activePlayerName) {
        lastGameState = state;
        client.sendMessage(new GameStateMessage(state,activePlayerName));
    }

    @Override
    public void receivePlayerList(List<Player> playerList) {
        client.sendMessage(new PlayerListMessage(playerList));
    }

    @Override
    public void receiveAllowedSquares(List<Coordinate> allowedTiles) {
        client.sendMessage(new AllowedSquaresMessage(allowedTiles));
    }

    @Override
    public void receiveAvailableGodList(List<God> gods) {
        client.sendMessage(new AvailableGodsMessage(gods));
    }

    @Override
    public void receiveTurnState(Turn.State state, Player player) {
        client.sendMessage(new TurnStateMessage(state, player));
    }

    @Override
    public void receiveActivePlayer(Player player) {
        client.sendMessage(new ActivePlayerMessage(player));
    }

    @Override
    public void receiveAllowedSquares(Builder builder, List<Coordinate> allowedTiles, boolean specialPower) {
        client.sendMessage(new AllowedSquaresMessage(builder, allowedTiles, specialPower));
    }

    @Override
    public void receiveBoard(Board board) {
        client.sendMessage(new BoardMessage(board));
    }

    @Override
    public void receiveBuildersPositions(List<Builder> builders) {
        client.sendMessage(new BuilderPositionMessage(builders));
    }

    @Override
    public void receiveUpdateDone() {
        client.sendMessage(new UpdateDoneMessage());
        /*if(lastGameState == Game.State.END_GAME) {
            controller.game().disconnectPlayer(this);
            client.closeConnection();
        }*/
    }

    @Override
    public void receiveDisconnect() {
        controller.game().disconnectPlayer(this);
        client.closeConnection();
    }
}
