package view.screens;

import model.*;
import network.messages.Message;
import network.messages.toserver.EndPhaseMessage;
import network.messages.toserver.FirstActionMessage;
import network.messages.toserver.PlaceBuilderMessage;
import network.messages.toserver.SelectCoordinateMessage;
import utils.Coordinate;
import view.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Logic view of the Board. Contains all data structures useful to board display
 * This class should be extended from all concrete view board screens
 */
public abstract class BoardScreen extends Screen {

    private String activePlayer;    //Player refer
    private Player activePlayerReference;
    private List<Player> players;
    private Board board;
    private List<Builder> currBuilders;
    private List<Builder> oldBuilders;
    private Builder selectedBuilder;
    private final List<Coordinate> highlightedCoordinates;
    private final HashMap<Builder, List<Coordinate>> normalAllowedSquares;
    private final HashMap<Builder, List<Coordinate>> specialAllowedSquares;
    private Game.State currentGameState;
    private Turn.State turnState;
    private boolean specialPowerSelected;
    private final WinnerScreenBuilder winnerSB;

    /**
     * Create the Board Screen
     * @param view A reference to the view manger
     * @param activePlayer The username of the first player that should make a "move"
     * @param players List of all players in game
     * @param preHighCoords List of all coordinates to highlight when the screen is built
     */
    public BoardScreen(ViewManager view, String activePlayer, List<Player> players, List<Coordinate> preHighCoords) {
        super(view);
        this.activePlayer = activePlayer;
        this.players = new ArrayList<>(players);
        this.board = new Board();
        this.currentGameState = Game.State.PLACE_BUILDER;
        this.currBuilders = new ArrayList<>();
        this.oldBuilders = new ArrayList<>();
        this.highlightedCoordinates = new ArrayList<>(preHighCoords);
        this.normalAllowedSquares = new HashMap<>();
        this.specialAllowedSquares = new HashMap<>();
        setActivePlayer(players.stream()
                .filter(player -> player.getName().equals(activePlayer)).findFirst().orElseThrow());
        winnerSB = new WinnerScreenBuilder(view.getScreenFactory());
    }

    /**
     * This method has to be called every time a square is selected from the player.
     * If the square is a valid selection the view logic.
     * If the square selected is a valid selection for builder placement a {@link PlaceBuilderMessage} will
     * be sent to the server.
     * If the square selected is valid for a move either {@link FirstActionMessage} or {@link SelectCoordinateMessage}
     * will be sent to the server
     * @param square The coordinate of the square selected
     * @throws IllegalValueException The coordinate is outside of the board
     * @throws IllegalActionException The square selection is not valid (square is not a builder or highlighted)
     */
    protected synchronized final void selectSquare(Coordinate square) throws IllegalValueException, IllegalActionException {
        if(!isActiveScreen()){
            throw new ActivityNotAllowedException();
        }
        if(!Board.checkValidCoordinate(square)){
            throw new IllegalValueException("Coordinate not valid");
        }
        if(currentGameState == Game.State.PLACE_BUILDER){
            processBuilderPlacement(square); //Builder has to be placed
        } else if(currentGameState == Game.State.TURN) {
            if(selectedBuilder == null) {
                processBuilderSelection(square); //Builder has to be selected
            } else {
                processCellSelection(square); //Action has to be done
            }
        } else {
            throw new IllegalStateException(); //END GAME
        }
    }

    private void processBuilderPlacement(Coordinate square) throws IllegalActionException{
        if(!highlightedCoordinates.contains(square)){
            throw new IllegalActionException("Square already occupied");
        }
        //Builder accepted
        activeScreen = false;
        view.sendMessage(new PlaceBuilderMessage(square));
    }

    private void processBuilderSelection(Coordinate square) throws IllegalActionException{
        Builder builder = getBoardOccupant(square).orElse(null);
        if(builder == null || !builder.getOwner().getName().equals(getThisPlayerName())){
            throw new IllegalActionException("Builder not present");
        }
        selectedBuilder = builder;
        //Set highlighted coordinates to display
        highlightedCoordinates.clear();
        if(!specialPowerSelected)
            highlightedCoordinates.addAll(normalAllowedSquares.get(selectedBuilder));
        else
            highlightedCoordinates.addAll(specialAllowedSquares.get(selectedBuilder));
    }

    private void processCellSelection(Coordinate square) throws IllegalActionException{
        if(!highlightedCoordinates.contains(square)){
            throw new IllegalActionException("Square not valid");
        }
        if(turnState == Turn.State.MOVE) {
            sendTurnActionMessage(new FirstActionMessage(selectedBuilder.getId(),square,specialPowerSelected));
        } else {
            sendTurnActionMessage(new SelectCoordinateMessage(square,specialPowerSelected));
        }
    }

    /**
     * Reverts all moves done but not already sent to the server (for example a builder selection)
     * @throws IllegalActionException The action is not allowed
     */
    protected synchronized final void resetPhase() throws IllegalActionException {
        if(!isActiveScreen()){
            throw new ActivityNotAllowedException();
        }
        highlightedCoordinates.clear();
        if(currentGameState != Game.State.TURN){
            throw new IllegalActionException("Command not allowed during current phase");
        }
        if(turnState == Turn.State.MOVE && selectedBuilder != null){
            selectedBuilder = null;
            highlightedCoordinates.addAll(getBuildersPositions(activePlayer));
        }
    }

    /**
     * Toggle special power moves, if they are available
     * @throws IllegalActionException Special power is not allowed
     */
    protected synchronized final void toggleSpecialPower() throws IllegalActionException {
        if(!isActiveScreen()){
            throw new ActivityNotAllowedException();
        }
        if(!specialPowerAvailable()){
            throw new IllegalActionException("Special power not available");
        }
        specialPowerSelected = !specialPowerSelected;
        if(selectedBuilder != null) { //If a builder is selected update the highlighted squares
            highlightedCoordinates.clear();
            if(specialPowerSelected) {
                highlightedCoordinates.addAll(specialAllowedSquares.get(selectedBuilder));
            } else {
                highlightedCoordinates.addAll(normalAllowedSquares.get(selectedBuilder));
            }
        }
    }

    /**
     * Ends an optional action phase
     * @throws IllegalActionException The phase is not optional and could not be skipped
     */
    protected synchronized final void endPhase() throws IllegalActionException {
        if(!isActiveScreen()){
            throw new ActivityNotAllowedException();
        }
        if(!endPhaseAvailable()) {
            throw new IllegalActionException("Skipping current phase is not allowed");
        }
        sendTurnActionMessage(new EndPhaseMessage());
    }

    /**
     * Checks if the special power option could be selected
     * @return true if the special power could be activated from the player
     */
    protected synchronized final boolean specialPowerAvailable() {
        return isActiveScreen() &&
                ((turnState == Turn.State.MOVE && !specialAllowedSquares.isEmpty()) ||
                 (turnState == Turn.State.BUILD && activePlayerReference.getGod().hasSpecialBuildPower()));
    }

    /**
     * Checks if the special power action could be selected
     * @return true if the reset phase action could be activated from the player
     */
    protected synchronized final boolean resetPhaseAvailable(){
        return isActiveScreen() && currentGameState == Game.State.TURN && turnState == Turn.State.MOVE;
    }

    /**
     * Checks if the special power is selected
     * @return true if the special power has been activated
     */
    protected synchronized final boolean specialPowerEnabled() {
        return specialPowerSelected;
    }

    /**
     * Checks if the end phase action (skip optional moves) could be selected
     * @return true if the end phase action is available
     */
    protected synchronized final boolean endPhaseAvailable() {
        return isActiveScreen() &&
                (turnState == Turn.State.ADDITIONAL_MOVE || turnState == Turn.State.ADDITIONAL_BUILD);
    }

    /**
     * Changes the current active player
     * Updates the screen accordingly to the new active player
     * @param activePlayer A reference to a player object containing the new active player
     */
    private void setActivePlayer(Player activePlayer){
        this.activePlayerReference = activePlayer;
        this.activePlayer = activePlayer.getName();
        activeScreen = activePlayer.getName().equals(getThisPlayerName()); //evaluate if this is an active screen
    }


    //Getters

    private Optional<Builder> getBoardOccupant(Coordinate coordinate){
        return currBuilders.stream().filter(b -> b.getSquare().getCoordinate().equals(coordinate)).findAny();
    }

    /**
     * Getter method for the player list
     * @return A copy of the player lust
     */
    protected final List<Player> getPlayers(){
        return new ArrayList<>(players);
    }

    /**
     * Getter method for the active player username
     * @return A string containing the username of the active player
     */
    protected final String getActivePlayer(){
        return activePlayer;
    }

    /**
     * Getter method for the board object
     * @return A board object representing the current state of the board
     */
    protected final Board getBoard(){
        return board;
    }

    /**
     * Getter method for the list of all builders in the board
     * @return A copy of the list of all builders in the board
     */
    protected final List<Builder> getCurrBuilders(){
        return new ArrayList<>(currBuilders);
    }

    /**
     * Get a specific builder object in the board
     * @param playerName The player controlling the builder
     * @param id Tbe id of the builder
     * @param old Selector of the list of builder (old builders or new builder)
     * @return If old is true the builder of the past board status will be returned, if old is false
     *  the current state of the builder will be returned
     */
    protected final Builder getBuilder(String playerName, int id, boolean old){
        Function<List<Builder>,Builder> findBuilder =
                list -> list.stream()
                        .filter(b -> b.getOwner().getName().equals(playerName))
                        .filter(b -> b.getId() == id)
                        .findAny().orElseThrow(IllegalArgumentException::new);
        if(old) {
            return findBuilder.apply(oldBuilders);
        } else {
            return findBuilder.apply(currBuilders);
        }
    }

    /**
     * Getter for the builder that is actually selected from the player. Could be null if no player is selected
     * @return Currently selected builder
     */
    protected final Builder getSelectedBuilder(){
        return selectedBuilder;
    }

    /**
     * Getter for the coordinates to highlight in the view
     * @return A list of all coordinates to higlight in the view
     */
    protected final List<Coordinate> getHighlightedCoordinates(){
        return new ArrayList<>(highlightedCoordinates);
    }

    /**
     * Getter for the current game state identifier
     * @return The current game state identifier
     */
    protected final Game.State getGameState(){
        return this.currentGameState;
    }

    /**
     * Getter for the current turn state identifier
     * @return The current turn state identifier
     */
    protected final Turn.State getTurnState(){
        return this.turnState;
    }

    private void sendTurnActionMessage(Message<RemoteView> message){
        activeScreen = false;           //Set as inactive screen (until the next server message)
        resetStatusVariables();
        view.sendMessage(message);  //Communicate to the server
    }

    private void resetStatusVariables() {
        //Clear all temporary status variables
        specialPowerSelected = false;
        highlightedCoordinates.clear();
        normalAllowedSquares.clear();
        specialAllowedSquares.clear();
    }

    private List<Coordinate> getBuildersPositions(String playerName){
        return currBuilders.stream()
                .filter(b -> b.getOwner().getName().equals(playerName))
                .map(b -> b.getSquare().getCoordinate())
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void receiveGameState(Game.State state, Player activePlayer) {
        setActivePlayer(activePlayer);
        currentGameState = state;
    }

    @Override
    public synchronized void receivePlayerList(List<Player> list) {
        this.players = new ArrayList<>(list);
        winnerSB.setPlayers(list);
    }

    @Override
    public synchronized void receiveTurnState(Turn.State state, Player player) {
        setActivePlayer(player);
        turnState = state;
        if(state == Turn.State.MOVE) {
            selectedBuilder = null; //Reset selected builder at the start of the turn
            resetStatusVariables();
            highlightedCoordinates.addAll(getBuildersPositions(player.getName()));
        }
    }

    @Override
    public synchronized void receiveAllowedSquares(List<Coordinate> coordinates){
        highlightedCoordinates.clear();
        highlightedCoordinates.addAll(coordinates);
    }

    @Override
    public synchronized void receiveAllowedSquares(Builder builder, List<Coordinate> allowedTiles, boolean specialPower) {
        if(specialPower){
            specialAllowedSquares.put(builder,new ArrayList<>(allowedTiles));
        } else {
            normalAllowedSquares.put(builder, new ArrayList<>(allowedTiles));
        }
        if(turnState != Turn.State.MOVE && isActiveScreen()) {
            highlightedCoordinates.clear();
            highlightedCoordinates.addAll(normalAllowedSquares.get(selectedBuilder));
        }
    }

    @Override
    public synchronized void receiveBoard(Board board) {
        this.board = board;
    }

    @Override
    public synchronized void receiveBuildersPositions(List<Builder> builders) {
        oldBuilders = currBuilders;
        currBuilders = new ArrayList<>(builders);
    }

    @Override
    public synchronized void receiveUpdateDone() {
        if(currentGameState == Game.State.END_GAME){
            view.changeActiveScreen(winnerSB.buildScreen());
        }
    }
}
