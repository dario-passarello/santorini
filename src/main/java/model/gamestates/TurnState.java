package model.gamestates;

import model.Builder;
import utils.Coordinate;

public interface TurnState {
    void onEntry();
    void onExit();

    void selectBuilder(Builder b);
    void useGodPower();
    void selectCoordinate(Coordinate c);
    void endPhase();

}
