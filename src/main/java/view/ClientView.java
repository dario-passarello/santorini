package view;

import model.*;
import model.gods.God;
import network.messages.MessageTarget;
import utils.Coordinate;

import java.util.List;
import java.util.Map;

/**
 * Defines a common interface for all Client-Side Views
 * Client side Views are the targets of all message sent from
 * the server using the @link{MessageToView} interface
 */
public abstract class ClientView implements View, MessageTarget{

    @Override
    public void receiveGameState(Game.State state, Player activePlayerName) {

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
    public void receiveAllowedSquares(Builder builder, List<Coordinate> allowedTiles, boolean specialPower) {

    }

    @Override
    public void receiveBoard(Board board) {

    }

    @Override
    public void receiveBuildersPositions(List<Builder> builders) {

    }


    @Override
    public void receiveUpdateDone() {

    }
}
