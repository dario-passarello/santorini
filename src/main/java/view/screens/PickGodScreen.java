package view.screens;

import model.Game;
import model.Player;
import network.messages.toserver.PickGodMessage;
import view.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    protected synchronized final String getActivePlayerName() {
        return activePlayerName;
    }

    protected final List<String> getAllGodToChoose() {
        return new ArrayList<>(godsToChoose);
    }

    protected synchronized final List<String> getGodsRemaining(){
        return new ArrayList<>(godsRemaining);
    }
    //Logic buttons
    protected synchronized void pickGod(String godName) throws IllegalValueException {
        if(!activeScreen){
            throw new ActivityNotAllowedException();
        }
        if(godsRemaining.contains(godName)){
            throw new IllegalValueException(ClientErrorMessages.INVALID_GOD);
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
