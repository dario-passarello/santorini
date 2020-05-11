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
import java.util.Map;

public class RemoteView implements GameObserver, TurnObserver, MessageTarget {

    private String playerName;
    private final Controller controller;
    private final ClientHandler client;

    public RemoteView(ClientHandler client, Controller controller, String playerName) {
        this.controller = controller;
        this.playerName = playerName;
        this.client = client;
    }

    public void sendMessage(Message<? extends MessageTarget>message) {
        client.sendMessage(message);
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
        client.sendMessage(new GameStateMessage(state,activePlayerName));
    }

    @Override
    public void receivePlayerOutcome(Player player, boolean winner) {
        client.sendMessage(new PlayerOutcomeMessage(player,winner));
    }


    @Override
    public void receivePlayerList(List<Player> playerList) {
        client.sendMessage(new PlayerListMessage(playerList));
    }

    @Override
    public void receiveAvailableGodList(List<God> gods) {
        client.sendMessage(new AvailableGodsMessage(gods));
    }

    @Override
    public void receiveStateChange(Turn.State state) {
        client.sendMessage(new TurnStateMessage(state));
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
    }
}
