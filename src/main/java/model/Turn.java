package model;

import model.turnstates.*;
import utils.Coordinate;

import java.util.List;

public class Turn {

    public final TurnState builderSelectionState;
    public final TurnState showWalkableDeselectableState;
    public final TurnState showWalkableUndeselectableState;
    public final TurnState showWalkableAdditionalState;
    public final TurnState builderSelectionSpecialState;
    public final TurnState showBuildableSpecialStartState;
    public final TurnState showBuildableFirstState;
    public final TurnState showBuildableAdditionalState;
    public final TurnState showBuildableSpecialDomeState;
    public final TurnState endTurnState;

    private Game game;
    private TurnState currentState;
    private Player currentPlayer;

    private Builder activeBuilder;

    public Turn(Game game, Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        this.game = game;
        //Instantiate all states
        builderSelectionState = new BuilderSelectionState(this,this.game,false);
        showWalkableDeselectableState = new ShowWalkableState(this,this.game,true,true);
        showWalkableUndeselectableState = new ShowWalkableState(this,this.game, false, true);
        showWalkableAdditionalState = new ShowWalkableState(this,this.game,false,false);
        builderSelectionSpecialState = new BuilderSelectionState(this,this.game,true);
        showBuildableSpecialStartState = new ShowBuildableSpecialState(this,this.game,false);
        showBuildableFirstState = new ShowBuildableState(this,this.game,true);
        showBuildableAdditionalState = new ShowBuildableState(this, this.game, true);
        showBuildableSpecialDomeState = new ShowBuildableSpecialState(this, this.game,true);
        endTurnState = new EndTurnState(this,this.game);

        currentState = builderSelectionState; //Set initial state
        List<Builder> builders = currentPlayer.getBuilders();
        //TODO Notify Observers - Builder position
        currentState.onEntry();
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
     * Set the current turn state
     * @param turn a turn state
     */
    public void setTurnState(TurnState turn) {
        this.currentState.onExit();
        this.currentState = turn;
        this.currentState.onEntry();
    }

    public Builder getActiveBuilder() {
        return activeBuilder;
    }

    public void setActiveBuilder(Builder activeBuilder) {
        this.activeBuilder = activeBuilder;
    }

    /**
     * Give as an input a builder
     * @param b A reference to a builder controlled from the current player
     */
    public void selectBuilder(Builder b) {
        //TODO
    }

    /**
     * Enable the use of a god power
     */
    public void useGodPower() {
        //TODO
    }

    /**
     * Input a coordinate of the board
     * @param c A coordinate object representing the coordinate of a board
     */
    public void selectCoordinate(Coordinate c) {
        //TODO
    }

    /**
     *  End the current turn phase and go to the next one
     */
    public void endPhase() {
        //TODO
    }

    public void nextTurn() {

    }

}
