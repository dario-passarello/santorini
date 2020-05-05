package model;

import utils.Coordinate;
import utils.Observer;

import java.util.List;
import java.util.Map;

public interface TurnObserver extends Observer {
    void receiveStateChange(Turn.State state);
    void receiveActivePlayer(Player player);
    void receiveAllowedSquares(Builder builder, List<Coordinate> allowedTiles);
    void receiveBoard(Board board);
    void receiveBuildersPositions(List<Builder> builders);
    void receiveSpecialPowerInfo(Map<Builder, List<Coordinate>> allowedPositions);
    void receiveUpdateDone();
}
