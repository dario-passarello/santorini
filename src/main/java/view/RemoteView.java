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

/**
 * This class represents the abstraction of the Client, observed from the point of view of the Server
 * It is the communication Link between the Client and the Server
 */
public class RemoteView implements GameObserver, TurnObserver, MessageTarget {

    private String playerName;
    private final Controller controller;
    private final ClientHandler client;
    private Game.State lastGameState;

    /**
     * The constructor method
     * @param client The clientHandler of the player
     * @param controller The controller class of the Game
     * @param playerName The name of the Player
     */
    public RemoteView(ClientHandler client, Controller controller, String playerName) {
        this.controller = controller;
        this.playerName = playerName;
        this.client = client;
    }

    /**
     * This method sends a message to the real client
     * @param message The message to send
     */
    public void sendMessage(Message<? extends MessageTarget>message) {
        if(client != null) {
            client.sendMessage(message);
        }
    }

    /**
     * Standard getter
     * @return The reference to the controller attribute
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Standard getter
     * @return The name of the player
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Standard Setter
     * @param playerName The name of the player to set
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public void receiveGameState(Game.State state, Player activePlayerName) {
        lastGameState = state;
        if(client != null) {
            client.sendMessage(new GameStateMessage(state, activePlayerName));
        }
    }

    @Override
    public void receivePlayerList(List<Player> playerList) {
        if(client != null) {
            client.sendMessage(new PlayerListMessage(playerList));
        }
    }

    @Override
    public void receiveAllowedSquares(List<Coordinate> allowedTiles) {
        if(client != null) {
            client.sendMessage(new AllowedSquaresMessage(allowedTiles));
        }
    }

    @Override
    public void receiveAvailableGodList(List<God> gods) {
        if(client != null) {
            client.sendMessage(new AvailableGodsMessage(gods));
        }
    }

    @Override
    public void receiveTurnState(Turn.State state, Player player) {
        if(client != null) {
            client.sendMessage(new TurnStateMessage(state, player));
        }
    }

    @Override
    public void receiveActivePlayer(Player player) {
        if(client != null) {
            client.sendMessage(new ActivePlayerMessage(player));
        }
    }

    @Override
    public void receiveAllowedSquares(Builder builder, List<Coordinate> allowedTiles, boolean specialPower) {
        if(client != null) {
            client.sendMessage(new AllowedSquaresMessage(builder, allowedTiles, specialPower));
        }
    }

    @Override
    public void receiveBoard(Board board) {
        if(client != null) {
            client.sendMessage(new BoardMessage(board));
        }
    }

    @Override
    public void receiveBuildersPositions(List<Builder> builders) {
        if(client != null) {
            client.sendMessage(new BuilderPositionMessage(builders));
        }
    }

    @Override
    public void receiveUpdateDone() {
        if(client != null) {
            client.sendMessage(new UpdateDoneMessage());
        }
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
