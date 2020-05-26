package model;

import utils.Coordinate;
import utils.Observer;

import java.util.List;

public interface TurnObserver extends Observer {
    /**
     * Send to observer the state of the turn
     * @param state The Turn.State representing the actual turn state
     * @param player
     */
    void receiveTurnState(Turn.State state, Player player);

    /**
     * Send to observer the player that is playing the current turn
     * @param player The player playing in the current trun
     */
    void receiveActivePlayer(Player player);

    /**
     * Send for a builder the tile where it could be moved or build
     * @param builder The builder for which the allowed Squares are provided
     * @param allowedTiles The squares where the builder could make his action
     * @param specialPower
     */
    void receiveAllowedSquares(Builder builder, List<Coordinate> allowedTiles, boolean specialPower);

    /**
     * Send a Board update to the observer
     * @param board The new Board object
     */
    void receiveBoard(Board board);

    /**
     * Send to observer a list with all builder objects in game
     * @param builders The list of builders
     */
    void receiveBuildersPositions(List<Builder> builders);


    /**
     * Notifies all observer that the update is done, and they could safely display the content
     */
    void receiveUpdateDone();
}
