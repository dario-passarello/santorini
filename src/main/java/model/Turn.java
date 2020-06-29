package model;

import model.turnstates.*;
import utils.Coordinate;
import utils.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * This class represents a Turn of the game. It contains all the references to the Turn State Machines as well as all the
 * methods required to handle it
 */
public class Turn implements Observable<TurnObserver> {

    /**
     * The list of names of the different States in the Turn State Machine
     */
    public enum State implements StateIdentifier {
        /**
         * The Move State
         */
        MOVE,
        /**
         * The Special Move State
         */
        SPECIAL_MOVE,
        /**
         * The Additional Move State
         */
        ADDITIONAL_MOVE,
        /**
         * The Build State
         */
        BUILD,
        /**
         * The Additional Build State
         */
        ADDITIONAL_BUILD,
        /**
         * The End Turn State
         */
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

    /**
     * The constructor method. It cretes a turn for a designated Player
     * @param game The reference to the current game
     * @param currentPlayer The player to which the turn is created for
     */
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


    /**
     * Standard getter of the attribute game
     * @return A reference to the instance of the current game
     */
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

    /**
     * Standard getter
     * @return A reference to the current active builder
     */
    public Builder getActiveBuilder() {
        return activeBuilder;
    }

    /**
     * Standard setter for the active builder
     * @param activeBuilder The active builder
     */
    public void setActiveBuilder(Builder activeBuilder) {
        this.activeBuilder = activeBuilder;
    }

    /**
     * This method calls first Selection method of the current Turn State
     * @param builderID The ID of the Builder selected
     * @param c The coordinate target
     * @param specialPower A parameter that specifies if the first action is a special one or not
     * @return True if the method is called from a legit state. False otherwise
     * @throws IllegalArgumentException when the Current State throws it
     */
    public boolean firstSelection(int builderID, Coordinate c, boolean specialPower) throws IllegalArgumentException {
        return currentState.firstSelection(builderID,c,specialPower);
    }

    /**
     * This method calls first Selection method of the current Turn State, but with a specialPower parameter automatically
     * set to false
     * @param builderID The ID of the Builder selected
     * @param c The coordinate target
     * @return True if the method is called from a legit state. False otherwise
     * @throws IllegalArgumentException when the Current State throws it
     */
    public boolean firstSelection(int builderID, Coordinate c) throws IllegalArgumentException {
        return firstSelection(builderID, c, false);
    }

    /**
     * Input a coordinate of the board
     * @param c A coordinate object representing the coordinate of a board
     * @param specialPower The parameter representing if the player decided to use a special power
     * @return True if the method is called from a legit state. False otherwise
     * @throws IllegalArgumentException when the Current State throws it
     */
    public boolean selectCoordinate(Coordinate c, boolean specialPower) throws IllegalArgumentException {
        return currentState.selectCoordinate(c,specialPower);
    }

    /**
     * Input a coordinate of the board
     * @param c A coordinate object representing the coordinate of a board
     * @return True if the method is called from a legit State. False otherwise
     * @throws IllegalArgumentException when the Current State throws it
     */
    public boolean selectCoordinate(Coordinate c) throws IllegalArgumentException {
        return currentState.selectCoordinate(c, false);
    }

    /**
     *  End the current additional turn phase and go to the next one
     * @return True if the method is called from a legit State. False otherwise
     * @throws IllegalStateException when the Current State throws it
     */
    public boolean endPhase() throws IllegalStateException {
        return currentState.endPhase();
    }

    /**
     * This method returns the ID of the current Turn State
     * @return The enum representing the ID of the current Turn State
     */
    public Turn.State getStateID(){
        return currentState.getStateID();
    }

    /**
     * This method is called whenever a turn ends
     */
    public void endTurn(){
        getCurrentPlayer().getGod().resetBehaviors();
        game.nextTurn(false);
    }

    /**
     * This method is called whenever a new Turn has to be created
     */
    public void newTurn() {
        if(currentPlayer.checkMovingLoseCondition()) {
            game.removePlayer(currentPlayer, false);
        } else {
            this.setTurnState(this.moveState);
            this.notifyObservers((TurnObserver obs) -> { //Start composite update of observers
                obs.receiveBoard(new Board(game.getBoard())); //Send a copyBehavior of the board
                obs.receiveBuildersPositions(game.getAllBuilders().stream() //Send a copyBehavior
                        .map(Builder::new)
                        .collect(Collectors.toList()));
                currentPlayer.getBuilders() //For each builder send his walkable neighbours
                        .forEach(builder -> obs.receiveAllowedSquares(builder, builder.getWalkableCoordinates(), false));
                if(currentPlayer.getGod().hasSpecialStartPower()) { //If has special power send his buildable neighbour for special power
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

