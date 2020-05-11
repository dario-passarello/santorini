package model;

import utils.Coordinate;
import utils.Observer;

import java.util.List;
import java.util.Map;

public interface TurnObserver extends Observer {
    /**
     * Send to observer the state of the turn
     * @param state The Turn.State representing the actual turn state
     */
    void receiveStateChange(Turn.State state);

    /**
     * Send to observer the player that is playing the current turn
     * @param player The player playing in the current trun
     */
    void receiveActivePlayer(Player player);

    /**
     * Send for a builder the tile where it could be moved or build
     * @param builder The builder for which the allowed Squares are provided
     * @param allowedTiles The squares where the builder could make his action
     */
    void receiveAllowedSquares(Builder builder, List<Coordinate> allowedTiles);
    void receiveBoard(Board board);
    void receiveBuildersPositions(List<Builder> builders);
    void receiveSpecialPowerInfo(Map<Builder, List<Coordinate>> allowedPositions);
    void receiveUpdateDone();
}
