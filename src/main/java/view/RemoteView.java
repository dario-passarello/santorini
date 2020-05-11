package view;

import controller.Controller;
import model.*;
import model.gods.God;
import network.ClientHandler;
import network.messages.MessageTarget;
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

    public void sendMessage() {

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

    }

    @Override
    public void receivePlayerOutcome(Player playerName, boolean winner) {

    }


    @Override
    public void receivePlayerList(List<Player> map) {

    }

    @Override
    public void receiveAvailableGodList(List<God> gods) {

    }

    @Override
    public void receiveStateChange(Turn.State state) {

    }

    @Override
    public void receiveActivePlayer(Player player) {

    }

    @Override
    public void receiveAllowedSquares(Builder builder, List<Coordinate> allowedTiles) {

    }

    @Override
    public void receiveBoard(Board board) {

    }

    @Override
    public void receiveBuildersPositions(List<Builder> builders) {

    }

    @Override
    public void receiveSpecialPowerInfo(Map<Builder, List<Coordinate>> allowedPositions) {

    }

    @Override
    public void receiveUpdateDone() {

    }
}
