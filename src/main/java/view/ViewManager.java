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
 * Handles the view screens, and calls server adapters methods in order to communicate
 * with the server
 */
public class ViewManager implements Runnable{

    private ServerAdapter serverAdapter;
    private ScreenFactory screenFactory;
    private Screen activeScreen;

    //Game persistent info
    private String thisPlayerName;
    private List<String> playersNames;
    private int numberOfPlayers;

    @Override
    public void run(){
        activeScreen = screenFactory.getMenuScreen();
        screenFactory.initialize(activeScreen);
        activeScreen.onScreenOpen();
    }

    /**
     * Sets up the factory for instantiating all view screens.
     * NOTE: This method should be called before run()
     * @param screenFactory A ScreenFactory implementation
     */
    public void setScreenFactory(ScreenFactory screenFactory){
        this.screenFactory = screenFactory;
    }

    /**
     * Open a connection with the server if it is not already open
     * @param ip The server hostname
     * @param port The port address of the server
     * @throws IOException The connection fails to open
     * @throws IllegalStateException The connection is already open
     */
    public void openConnection(String ip, int port) throws IOException {
        if(serverAdapter != null) {
            throw new IllegalStateException(ClientErrorMessages.CONNECTION_ALREADY_OPEN);
        }
        serverAdapter = new ServerAdapter(this,ip,port);
        Thread adapterThread = new Thread(serverAdapter);
        adapterThread.start();
    }

    /**
     * Close the connection to the server
     * @throws IllegalStateException The connection is not open
     */
    public void closeConnection() {
        if(serverAdapter == null) {
            throw new IllegalStateException(ClientErrorMessages.CONNECTION_CLOSED);
        }
        serverAdapter.closeConnection();
        serverAdapter = null;
    }

    /**
     * Sends a message to the server
     * @param messageToServer The Message to the server with a vaild charater
     * @throws IllegalStateException The connection is not open
     */
    public void sendMessage(Message<? extends MessageTarget> messageToServer){
        if(serverAdapter == null) {
            throw new IllegalStateException(ClientErrorMessages.CONNECTION_CLOSED);
        }
        serverAdapter.sendMessage(messageToServer);
    }

    /**
     * Receive a message to forward to the screen
     * NOTE: This method is called from the context of the Server Adapter Listener Thread
     * @param messageToScreen The message to a Screen
     */
    public void receiveMessage(Message<Screen> messageToScreen){
        messageToScreen.execute(activeScreen);
    }

    /**
     * Change the screen seen by the users. Doing these calls the
     * onScreenClose() method for the old active screen, and then calls
     * the onScreenOpen() method for the new active screen
     * @param nextActiveScreen The new screen to display
     */
    public void changeActiveScreen(Screen nextActiveScreen){
        activeScreen.onScreenClose();
        activeScreen = nextActiveScreen;
        activeScreen.onScreenOpen();
    }

    /**
     * @return The current screen factory
     */
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
