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

public abstract class BoardScreen extends Screen {

    private String activePlayer;
    private List<Player> players;
    private Board board;
    private List<Builder> allBuilders;
    private Builder selectedBuilder;
    private List<Coordinate> highlightedCoordinates;
    private HashMap<Builder, List<Coordinate>> normalAllowedSquares;
    private HashMap<Builder, List<Coordinate>> specialAllowedSquares;
    private Game.State currentGameState;
    private Turn.State turnState;
    private boolean specialPowerSelected;
    private boolean endAvailable;

    public BoardScreen(ViewManager view, String activePlayer, List<Player> players) {
        super(view);
        this.activePlayer = activePlayer;
        this.players = new ArrayList<>(players);
        this.board = new Board();
        setActivePlayer(activePlayer);
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
        }
    }

    private void processBuilderPlacement(Coordinate square) throws IllegalActionException{
        if(!board.getFreeCoordinates().contains(square)){
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
        if(!specialPowerSelected)
            highlightedCoordinates = normalAllowedSquares.get(selectedBuilder);
        else
            highlightedCoordinates = specialAllowedSquares.get(selectedBuilder);
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
        if(currentGameState != Game.State.TURN){
            throw new IllegalActionException("Command not allowed during current phase");
        }
        if(turnState == Turn.State.MOVE){
            selectedBuilder = null;
        }
        highlightedCoordinates.clear();
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
     * @return true if the special power could be activated from the player
     */
    protected synchronized final boolean specialPowerAvailable() {
        return isActiveScreen() && !specialAllowedSquares.isEmpty();
    }

    /**
     * @return true if the special power has been activated
     */
    protected synchronized final boolean specialPowerEnabled() {
        return specialPowerSelected;
    }

    protected synchronized final boolean endPhaseAvailable() {
        return turnState == Turn.State.ADDITIONAL_MOVE || turnState == Turn.State.ADDITIONAL_BUILD;
    }

    private void setActivePlayer(String activePlayerName){
        this.activePlayer = activePlayerName;
        activeScreen = activePlayerName.equals(getThisPlayerName()); //evaluate if this is an active screen
    }


    //Getters

    private Optional<Builder> getBoardOccupant(Coordinate coordinate){
        return allBuilders.stream().filter(b -> b.getSquare().getCoordinate().equals(coordinate)).findAny();
    }

    protected List<Player> getPlayers(){
        return players;
    }

    protected String getActivePlayer(){
        return activePlayer;
    }

    protected Board getBoard(){
        return board;
    }

    protected List<Builder> getAllBuilders(){
        return allBuilders;
    }

    protected Builder getSelectedBuilder(){
        return selectedBuilder;
    }

    protected List<Coordinate> getHighlightedCoordinates(){
        return highlightedCoordinates;
    }




    private void sendTurnActionMessage(Message<RemoteView> message){
        activeScreen = false;
        highlightedCoordinates.clear();
        normalAllowedSquares.clear();
        specialAllowedSquares.clear();
        view.sendMessage(message);
    }

    private void turnReset(){
        specialPowerSelected = false;
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
        this.players = new ArrayList<>(list);
    }

    @Override
    public void receiveTurnState(Turn.State state, Player player) {
        setActivePlayer(player.getName());
        turnState = state;
    }

    @Override
    public void receiveAllowedSquares(Builder builder, List<Coordinate> allowedTiles, boolean specialPower) {
        super.receiveAllowedSquares(builder, allowedTiles, specialPower);
    }

    @Override
    public void receiveBoard(Board board) {
        this.board = board;
    }

    @Override
    public void receiveBuildersPositions(List<Builder> builders) {
        super.receiveBuildersPositions(builders);
    }

    @Override
    public void receiveUpdateDone() {
        super.receiveUpdateDone();
    }

}
