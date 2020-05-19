package view;

import model.*;
import model.gods.God;
import utils.Coordinate;

import java.util.List;

public class Screen implements View {

    protected final ViewManager view;

    public Screen(ViewManager view){
        this.view = view;
    }

    public ViewManager getView() {
        return view;
    }


    @Override
    public void receiveGameState(Game.State state, Player activePlayerName) {

    }

    @Override
    public void receivePlayerOutcome(Player playerName, boolean winner) {

    }

    @Override
    public void receivePlayerList(List<Player> list) {

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
