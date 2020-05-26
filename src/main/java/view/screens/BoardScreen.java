package view.screens;

import model.*;
import utils.Coordinate;
import view.IllegalActionException;
import view.IllegalValueException;
import view.ViewManager;

import java.util.ArrayList;
import java.util.List;

public abstract class BoardScreen extends Screen {

    private String activePlayer;
    private List<Player> players;
    private Board board;
    private List<Builder> allBuilders;
    private Game.State currentGameState;

    public BoardScreen(ViewManager view, String activePlayer, List<Player> players) {
        super(view);
        this.activePlayer = activePlayer;
        this.players = new ArrayList<>(players);
        setActivePlayer(activePlayer);
    }

    protected void selectSquare(Coordinate square) throws IllegalValueException, IllegalActionException {
        if(!Board.checkValidCoordinate(square)){
            throw new IllegalValueException("Coordinate not valid");
        }

        

    }

    @Override
    public void receiveGameState(Game.State state, Player activePlayer) {
        setActivePlayer(activePlayer.getName());
        currentGameState = state;
    }

    @Override
    public void receivePlayerOutcome(Player playerName, boolean winner) {
        super.receivePlayerOutcome(playerName, winner);
    }

    @Override
    public void receivePlayerList(List<Player> list) {
        super.receivePlayerList(list);
    }


    @Override
    public void receiveTurnState(Turn.State state, Player player) {
        super.receiveTurnState(state, player);
    }

    @Override
    public void receiveActivePlayer(Player player) {
        super.receiveActivePlayer(player);
    }

    @Override
    public void receiveAllowedSquares(Builder builder, List<Coordinate> allowedTiles, boolean specialPower) {
        super.receiveAllowedSquares(builder, allowedTiles, specialPower);
    }

    @Override
    public void receiveBoard(Board board) {
        super.receiveBoard(board);
    }

    @Override
    public void receiveBuildersPositions(List<Builder> builders) {
        super.receiveBuildersPositions(builders);
    }

    @Override
    public void receiveUpdateDone() {
        super.receiveUpdateDone();
    }

    private void setActivePlayer(String activePlayerName){
        this.activePlayer = activePlayerName;
        activeScreen = activePlayerName.equals(getThisPlayerName()); //evaluate if this is an active screen
    }
}
