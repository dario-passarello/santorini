package model;

import model.turnstates.*;
import utils.Coordinate;
import utils.Observable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Turn implements Observable<TurnObserver> {

    public enum State {
        MOVE,
        ADDITIONAL_MOVE,
        BUILD,
        END_TURN;
    }

    public final TurnState moveState;
    public final TurnState additionalMoveState;
    public final TurnState specialMoveState;
    public final TurnState buildState;
    public final TurnState additionalBuildState;
    public final TurnState endTurnState;

    private Game game;
    private TurnState currentState;
    private Player currentPlayer;
    private Builder activeBuilder;

    private List<TurnObserver> observers;

    public Turn(Game game, Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        this.game = game;
        //Instantiate all states
        moveState = new MoveState(this, this.game);
        additionalMoveState = new AdditionalMoveState(this, this.game, true);
        specialMoveState = new AdditionalMoveState(this, this.game, false);
        buildState = new BuildState(this, this.game,false);
        additionalBuildState = new BuildState(this, this.game,true );
        endTurnState = new EndTurnState(this, this.game);
    }



    public Game getGame() {
        return game;
    }

    /**
     *
     * @return a reference to the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @return a reference to the current state
     */
    public TurnState getTurnState() {
        return this.currentState;
    }

    /**
     * Set the current turn state and notifies the observers for the state change
     * @param state a turn state
     */
    public void setTurnState(TurnState state) {
        this.currentState = state;
        notifyObservers((TurnObserver obs) -> obs.receiveStateChange(currentState.getStateID()));
    }

    public Builder getActiveBuilder() {
        return activeBuilder;
    }

    public void setActiveBuilder(Builder activeBuilder) {
        this.activeBuilder = activeBuilder;
    }

    public boolean firstSelection(Builder  b, Coordinate c, boolean specialPower) {
        return currentState.firstSelection(b,c,specialPower);
    }

    public boolean firstSelection(Builder b, Coordinate c) {
        return currentState.firstSelection(b,c);
    }

    /**
     * Input a coordinate of the board
     * @param c A coordinate object representing the coordinate of a board
     */
    public boolean selectCoordinate(Coordinate c, boolean specialPower) {
        return currentState.selectCoordinate(c,specialPower);
    }

    /**
     * Input a coordinate of the board
     * @param c A coordinate object representing the coordinate of a board
     */
    public boolean selectCoordinate(Coordinate c) {
        return currentState.selectCoordinate(c);
    }

    /**
     *  End the current additional turn phase and go to the next one
     */
    public boolean endPhase() {
        return currentState.endPhase();
    }

    public void newTurn() {
        if(currentPlayer.checkMovingLoseCondition()) {
            game.removePlayer(currentPlayer);
        } else {
            this.setTurnState(this.moveState);
            this.notifyObservers((TurnObserver obs) -> { //Start composite update of observers
                obs.receiveActivePlayer(currentPlayer.getName()); //Send current player
                obs.receiveBoard(new Board(game.getBoard())); //Send a copy of the board
                obs.receiveBuildersPositions(currentPlayer.getBuilders());
                currentPlayer.getBuilders() //For each builder send his walkable neighbours
                        .forEach(builder -> obs.receiveAllowedSquares(builder, builder.getWalkableNeighborhood()));
                if(currentPlayer.getGod().hasSpecialBuildPower()) { //If has special power send his buildable neighbour for special power
                    obs.receiveSpecialPowerInfo(currentPlayer.getBuilders().stream()
                            .collect(Collectors.toMap(Function.identity(), Builder::getBuildableNeighborhood)));
                }
                obs.receiveUpdateDone(); //Send the update done signal for the composite update
            });
        }
    }

    @Override
    public void registerObserver(TurnObserver m) {
        observers.add(m);
    }

    @Override
    public void unregisterObserver(TurnObserver m) {
        observers.remove(m);
    }

    @Override
    public void notifyObservers(Consumer<TurnObserver> notifyAction) {
        observers.forEach(notifyAction);
    }

}

