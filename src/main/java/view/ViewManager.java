package view;

import network.ServerAdapter;
import network.messages.Message;
import network.messages.MessageTarget;
import view.screens.Screen;
import view.screens.ScreenFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Defines a common interface for all Client-Side Views
 * Client side Views are the targets of all message sent from
 * the server using the @link{MessageToView} interface
 */
public class ViewManager{

    private ServerAdapter serverAdapter;
    private final ScreenFactory screenFactory;
    private Screen activeScreen;

    //Game persistent info
    private String thisPlayerName;
    private List<String> playersNames;
    private int numberOfPlayers;

    public ViewManager(ScreenFactory screenFactory) {
        this.screenFactory = screenFactory;
    }


    public void openConnection(String ip, int port) throws IOException {
        if(serverAdapter != null) {
            throw new IllegalStateException(ClientErrorMessages.CONNECTION_ALREADY_OPEN);
        }
        serverAdapter = new ServerAdapter(this,ip,port);
        Thread adapterThread = new Thread(serverAdapter);
        adapterThread.start();
    }

    public void closeConnection() {
        if(serverAdapter == null) {
            throw new IllegalStateException(ClientErrorMessages.CONNECTION_CLOSED);
        }
        serverAdapter.closeConnection();
        serverAdapter = null;
    }

    public void sendMessage(Message<? extends MessageTarget> messageToServer){
        if(serverAdapter == null) {
            throw new IllegalStateException(ClientErrorMessages.CONNECTION_CLOSED);
        }
        serverAdapter.sendMessage(messageToServer);
    }

    public void receiveMessage(Message<Screen> messageToScreen){
        messageToScreen.execute(activeScreen);
    }

    public void changeActiveScreen(Screen nextActiveScreen){
        activeScreen.onScreenClose();
        activeScreen = nextActiveScreen;
        activeScreen.onScreenOpen();
    }

    public ScreenFactory getScreenFactory() {
        return screenFactory;
    }

    public String getThisPlayerName() {
        return thisPlayerName;
    }

    public void setThisPlayerName(String thisPlayerName) {
        this.thisPlayerName = thisPlayerName;
    }

    public List<String> getPlayersNames() {
        return new ArrayList<>(playersNames);
    }

    public void setPlayersNames(List<String> playersNames) {
        this.playersNames = new ArrayList<>(playersNames);
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}
