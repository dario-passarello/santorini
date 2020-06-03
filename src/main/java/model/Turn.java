package model;

import model.turnstates.*;
import utils.Coordinate;
import utils.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Turn implements Observable<TurnObserver> {

    public enum State implements StateIdentifier {
        MOVE,
        SPECIAL_MOVE,
        ADDITIONAL_MOVE,
        BUILD,
        ADDITIONAL_BUILD,
        END_TURN;
    }

    public final TurnState moveState;
    public final TurnState additionalMoveState;
    public final TurnState specialMoveState;
    public final TurnState buildState;
    public final TurnState additionalBuildState;

    private final Game game;
    private TurnState currentState;
    private final Player currentPlayer;
    private Builder activeBuilder;

    private List<TurnObserver> observers;

    public Turn(Game game, Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        this.game = game;
        observers = new ArrayList<>();
        //Instantiate all states
        moveState = new MoveState(this, this.game);
        additionalMoveState = new AdditionalMoveState(this, this.game, true);
        specialMoveState = new AdditionalMoveState(this, this.game, false);
        buildState = new BuildState(this, this.game, false);
        additionalBuildState = new BuildState(this, this.game, true);
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
        notifyObservers((TurnObserver obs) -> obs.receiveTurnState(currentState.getStateID(), new Player(currentPlayer)));
    }

    public Builder getActiveBuilder() {
        return activeBuilder;
    }

    public void setActiveBuilder(Builder activeBuilder) {
        this.activeBuilder = activeBuilder;
    }

    public boolean firstSelection(int builderID, Coordinate c, boolean specialPower) throws IllegalArgumentException {
        return currentState.firstSelection(builderID,c,specialPower);
    }

    public boolean firstSelection(int builderID, Coordinate c) throws IllegalArgumentException {
        return firstSelection(builderID, c, false);
    }

    /**
     * Input a coordinate of the board
     * @param c A coordinate object representing the coordinate of a board
     */
    public boolean selectCoordinate(Coordinate c, boolean specialPower) throws IllegalArgumentException {
        return currentState.selectCoordinate(c,specialPower);
    }

    /**
     * Input a coordinate of the board
     * @param c A coordinate object representing the coordinate of a board
     */
    public boolean selectCoordinate(Coordinate c) throws IllegalArgumentException {
        return currentState.selectCoordinate(c, false);
    }

    /**
     *  End the current additional turn phase and go to the next one
     */
    public boolean endPhase() throws IllegalStateException {
        return currentState.endPhase();
    }

    public Turn.State getStateID(){
        return currentState.getStateID();
    }

    public void endTurn(){
        getCurrentPlayer().getGod().resetBehaviors();
        game.nextTurn(false);
    }

    public void newTurn() {
        if(currentPlayer.checkMovingLoseCondition()) {
            game.removePlayer(currentPlayer, false);
        } else {
            this.setTurnState(this.moveState);
            this.notifyObservers((TurnObserver obs) -> { //Start composite update of observers
                obs.receiveBoard(new Board(game.getBoard())); //Send a copy of the board
                obs.receiveBuildersPositions(currentPlayer.getBuilders().stream() //Send a copy
                        .map(Builder::new)
                        .collect(Collectors.toList()));
                currentPlayer.getBuilders() //For each builder send his walkable neighbours
                        .forEach(builder -> obs.receiveAllowedSquares(builder, builder.getWalkableCoordinates(), false));
                if(currentPlayer.getGod().hasSpecialBuildPower()) { //If has special power send his buildable neighbour for special power
                    currentPlayer.getBuilders()
                            .forEach(builder -> obs.receiveAllowedSquares(builder, builder.getBuildableCoordinates(), true));
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

