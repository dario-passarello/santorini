package view.screens;

import model.Game;
import model.Player;
import network.messages.toserver.PickGodMessage;
import view.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * In this screen all god chosen in the GodSelectionPhase should be displayed.
 * Every player, in the inverse rotation, will pick the god. Once a god is picked from one
 * player, the other players could not pick that god. The last player should not pick a god
 * because he will get the last god remaining.
 */
public abstract class PickGodScreen extends Screen{

    private String activePlayerName;
    private final List<String> godsToChoose;
    private final List<String> godsRemaining;
    private final BoardScreenBuilder boardScreenBuilder;

    private Game.State nextState;

    public PickGodScreen(ViewManager view, String firstActivePlayer, List<String> availableGods) {
        super(view);
        godsToChoose = new ArrayList<>(availableGods);
        godsRemaining = new ArrayList<>(availableGods);
        activePlayerName = firstActivePlayer;
        activeScreen = getThisPlayerName().equals(activePlayerName);
        boardScreenBuilder = new BoardScreenBuilder(view.getScreenFactory());
    }

    //Getters

    /**
     * @return The player name of the player that has to pick the god
     */
    protected synchronized final String getActivePlayerName() {
        return activePlayerName;
    }

    /**
     * @return List of the god identifiers available in this game
     */
    protected final List<String> getAllGodToChoose() {
        return new ArrayList<>(godsToChoose);
    }

    /**
     * @return List of the god identifiers not picked from the other players
     */
    protected synchronized final List<String> getGodsRemaining(){
        return new ArrayList<>(godsRemaining);
    }
    //Logic buttons
    /**
     * The command method to pick a god, this method will check if the choice is
     * valid and then send a {@link PickGodMessage} to the controller containing the
     * identifier of the picked God
     * @param godName the identifier of the god picked from the player
     * @throws ActivityNotAllowedException If it's up to this player to pick the god in this turn
     * @throws IllegalValueException If the  {@param godName} is not an identifier of a god available for being picked
     *          from the player*/
    protected synchronized void pickGod(String godName) throws IllegalValueException {
        if(!activeScreen){
            throw new ActivityNotAllowedException();
        }
        if(!godsRemaining.contains(godName)){
            throw new IllegalValueException(ClientErrorMessages.INVALID_GOD + ": " + godName);
        }
        godsRemaining.clear();
        view.sendMessage(new PickGodMessage(godName));
    }

    //Server Listener
    @ServerListener
    @Override
    public synchronized void receiveGameState(Game.State state, Player activePlayer) {
        nextState = state;
        boardScreenBuilder.setActivePlayer(activePlayerName);
        activePlayerName = activePlayer.getName();
        if(state == Game.State.GOD_PICK)
            activeScreen = activePlayerName.equals(getThisPlayerName());
    }

    @ServerListener
    @Override
    public synchronized void receivePlayerList(List<Player> list) {
        boardScreenBuilder.setPlayerList(list);
        //Remove god already chosen by other players
        godsRemaining.removeAll(list.stream()
                .filter(player -> player.getGod() != null)
                .map(player -> player.getGod().getName())
                .collect(Collectors.toList()));
    }

    @ServerListener
    @Override
    public synchronized void receiveUpdateDone() {
        if(nextState != Game.State.GOD_PICK){
            ScreenBuilder nextScreen;
            if(nextState == Game.State.END_GAME){
                nextScreen = new ScreenBuilder(view.getScreenFactory()) {
                    public Screen buildScreen() {
                        return screenFactory.getMenuScreen();
                    }
                };
            } else if(nextState == Game.State.PLACE_BUILDER) {
                nextScreen = new BoardScreenBuilder(view.getScreenFactory());
            } else {
                throw new ProtocolViolationException();
            }
            view.changeActiveScreen(nextScreen.buildScreen());
        }

    }


}
