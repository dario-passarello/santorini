package view.screens;

import model.Game;
import model.Player;
import model.gods.God;
import network.messages.toserver.SubmitGodListMessage;
import utils.Coordinate;
import view.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This screen should display all the choosable gods of the game
 * The first player can select two or three gods (according to the number of players)
 * then he can submit the choices and move the game to the {@link PickGodScreen}
 */
public abstract class GodSelectionScreen extends Screen {

    private final Set<String> chosenGods;

    private final PickGodScreenBuilder pickGodSB;
    private final BoardScreenBuilder boardSB;
    private Game.State nextState;



    public GodSelectionScreen(ViewManager view, String activePlayer) {
        super(view);
        pickGodSB = new PickGodScreenBuilder(view.getScreenFactory());
        boardSB = new BoardScreenBuilder(view.getScreenFactory());
        this.chosenGods = new HashSet<>();
        this.activeScreen = getThisPlayerName().equals(activePlayer); //Check if the players should make the choice
   }

    //Getters


    /**
     * @return A list with the gods chosen from the player, if the players
     *          chooses mortal, a list containing a number of "Mortal" equals
     *          to the number of players will be returned (for compatibility with
     *          the model)
     *
     */
    protected final List<String> getChosenGodList(){
        if(chosenGods.contains(AssetLoader.MORTAL_NAME)){
            ArrayList<String> mortals = new ArrayList<>();
            for(int i = 0; i < getNumberOfPlayers(); i++){
                mortals.add(AssetLoader.MORTAL_NAME);
            }
            return mortals;
        } else {
            return new ArrayList<>(chosenGods);
        }
    }

    /**
     * @param god The god to search in the set of chosen gods
     * @return true if the god is in the chosen list
     */
    protected final boolean isGodChosen(String god) {
        return chosenGods.contains(god);
    }

    protected final boolean readyToSubmit() {
        return chosenGods.stream().mapToInt(this::godWeight).sum() == getNumberOfPlayers();
    }

    //Logic Fields

    /**
     * Adds a god in the list of god chosen if there is space
     * @param name The name identifier of the god to add
     * @throws IllegalValueException The identifier of the god is not valid
     * @throws IllegalActionException The set of gods is full because the number of gods
     *         chosen is equal to the number of the players or if a mortal is in the list.
     * @throws ActivityNotAllowedException It's not up to this Player to choose the gods.
     */
    protected final void addGod(String name) throws IllegalValueException, IllegalActionException {
        if(!isActiveScreen()){
            throw new ActivityNotAllowedException(ClientErrorMessages.ACTIVITY_NOT_ALLOWED);
        }
        if(!AssetLoader.isAGod(name)) {
            throw new IllegalValueException(ClientErrorMessages.INVALID_GOD);
        }
        int weight = godWeight(name) + chosenGods.stream().mapToInt(this::godWeight).sum();
        if(weight > getNumberOfPlayers()) {
            throw new IllegalActionException(ClientErrorMessages.GOD_LIST_FULL);
        }
        chosenGods.add(name);

    }

    /**
     * Remove an chosen god from the chosen god list
     * @param name The string identifier of the god to be removed
     * @throws IllegalValueException The identifier of the god is not valid
     * @throws IllegalActionException The set of chosen gods is empty
     * @throws ActivityNotAllowedException It's not up to this Player to choose the gods.
     */
    protected final void removeGod(String name) throws IllegalValueException, IllegalActionException {
        if(!isActiveScreen()){
            throw new ActivityNotAllowedException(ClientErrorMessages.ACTIVITY_NOT_ALLOWED);
        }
        if(chosenGods.isEmpty()){
            throw new IllegalActionException(ClientErrorMessages.GOD_LIST_EMPTY);
        }
        if(!chosenGods.contains(name)) {
            throw new IllegalValueException(ClientErrorMessages.GOD_NOT_PRESENT);
        }
        chosenGods.remove(name);
    }

    //Logic commands
    protected final void submitGodList() throws IllegalActionException {
        if(!isActiveScreen()){
            throw new ActivityNotAllowedException(ClientErrorMessages.ACTIVITY_NOT_ALLOWED);
        }
        if(!readyToSubmit()){
            throw new IllegalActionException(ClientErrorMessages.DATA_INCOMPLETE);
        }
        view.sendMessage(new SubmitGodListMessage(new ArrayList<>(chosenGods)));
    }

    //Server Listeners
    @ServerListener
    @Override
    public synchronized void receiveGameState(Game.State state, Player activePlayer) {
        nextState = state;
        pickGodSB.setActivePlayer(activePlayer.getName());
        boardSB.setActivePlayer(activePlayer.getName());

    }

    @ServerListener
    @Override
    public synchronized void receivePlayerList(List<Player> player) {
        boardSB.setPlayerList(player);
    }

    @ServerListener
    @Override
    public synchronized void receiveAllowedSquares(List<Coordinate> coordinates) {
        boardSB.setPreHighlightedCoordinates(coordinates);
    }

    @ServerListener
    @Override
    public synchronized void receiveAvailableGodList(List<God> gods) {
        pickGodSB.setAvailableGods(gods.stream().map(God::getName).collect(Collectors.toList()));
    }

    @ServerListener
    @Override
    public synchronized void receiveUpdateDone(){
        ScreenBuilder nextScreen;
        if(nextState == Game.State.END_GAME){
            nextScreen = new ScreenBuilder(view.getScreenFactory()) {
                public Screen buildScreen() {
                    return screenFactory.getConnectionErrorScreen();
                }
            };
        } else if(nextState == Game.State.GOD_PICK) {
            nextScreen = pickGodSB;
        } else if(nextState == Game.State.PLACE_BUILDER) {
            nextScreen = boardSB;
        }
        else {
            throw new ProtocolViolationException();
        }
        view.changeActiveScreen(nextScreen.buildScreen());
    }

    private int godWeight(String god) {
        return god.equals(AssetLoader.MORTAL_NAME) ? getNumberOfPlayers() : 1;
    }


}
