package view.screens;


import model.Game;
import model.Player;
import network.messages.toserver.LoginDataMessage;
import view.*;

import java.io.IOException;
import java.util.List;

public abstract class ConnectionScreen extends Screen {

    public static final int MAX_USERNAME_LENGTH = 30;
    public static final int MIN_USERNAME_LENGTH = 3;

    private String ip;
    private String username;
    private int port;
    private int numberOfPlayers;

    private GodSelectionScreenBuilder screenBuilder;
    private List<String> players;
    private String activePlayerName;

    public ConnectionScreen(ViewManager view) {
        super(view);

    }

    //Logic fields setter
    /**
     * Sets the screen ip address
     * @param ip An ip address
     * @throws IllegalArgumentException if the ip string is empty
     */
    protected final void setIP(String ip){
        if(ip.isEmpty()) {
            throw new IllegalArgumentException(ClientErrorMessages.EMPTY_ADDRESS);
        }
        this.ip = ip;
    }

    /**
     * Sets the screen port address
     * @param port an integer between 0 and 65535, represent the port number of the remote server
     * @throws IllegalArgumentException if the port number is not valid
     */
    protected final void setPort(int port){
        if(port < 0 || port > 65535) {
            throw new IllegalArgumentException(ClientErrorMessages.INVALID_PORT);
        }
        this.port = port;
    }


    /**
     * Sets the number of players preferred from the user
     * @param n number of player, must be 2 or 3
     * @throws IllegalArgumentException if number of players is not 2 or 3
     */
    protected void setNumberOfPlayers(int n){
        if(n != 2 && n != 3){
            throw new IllegalArgumentException(ClientErrorMessages.INVALID_NUMBER_OF_PLAYERS);
        }
        this.numberOfPlayers = n;
    }

    /**
     *  Sets the screen username
     *  @param username The name, should be at least {@value MIN_USERNAME_LENGTH}
     *                  and less than {@value MAX_USERNAME_LENGTH} chars long
     */
    protected final void setUsername(String username) throws IllegalValueException {
        if(username.length() < MIN_USERNAME_LENGTH) {
            throw new IllegalValueException(ClientErrorMessages.SHORT_USERNAME);
        }
        if(username.length() > MAX_USERNAME_LENGTH) {
            throw new IllegalValueException(ClientErrorMessages.LONG_USERNAME);
        }
        this.username = username;
    }

    //Logic buttons

    /**
     *   Try to connect to the server with the ip and port.
     *   This function waits until the match is found. Loading screen should be shown before calling this function
     */
    protected final void connect() throws IllegalActionException, IOException {
        if(port < 0 || port > 65535 || username == null || (numberOfPlayers != 2 && numberOfPlayers != 3)) {
            throw new IllegalActionException(ClientErrorMessages.DATA_INCOMPLETE);
        }
        screenBuilder = new GodSelectionScreenBuilder(view.getScreenFactory());
        view.openConnection(ip,port);
        view.sendMessage(new LoginDataMessage(username,numberOfPlayers));
        view.setNumberOfPlayers(numberOfPlayers);
        screenBuilder.buildScreen();
        view.setThisPlayerName(username);
        view.setPlayersNames(players);
        view.changeActiveScreen(view.getScreenFactory().getGodSelectionScreen(activePlayerName));
    }

    @Override
    @ServerListener
    public void receiveMatchFound(String playerName, List<String> players) {
        username = playerName; //Set server assigned username
        this.players = players;
        screenBuilder.setStateReceived();
    }

    @Override
    @ServerListener
    public void receiveGameState(Game.State state, Player activePlayerName) {
        if(state != Game.State.GOD_SELECTION) {
            throw new ProtocolViolationException();
        }
        screenBuilder.setActivePlayer(activePlayerName.getName());
    }

}
