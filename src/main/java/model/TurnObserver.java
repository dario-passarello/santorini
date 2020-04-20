package model;

import utils.Observer;

import java.util.List;
import java.util.Map;

public interface TurnObserver extends Observer {
    void receiveStateChange(Turn.State state);
    void receiveActivePlayer(String name);
    void receiveAllowedSquares(Builder builder, List<Square> allowedTiles);
    void receiveBoard(Board board);
    void receiveBuildersPositions(List<Builder> builders);
    void receiveSpecialPowerInfo(Map<Builder, List<Square>> allowedPositions);
    void receiveUpdateDone();
}
