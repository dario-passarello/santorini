package view.screens;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Game;
import model.Player;
import network.Client;
import network.messages.toserver.LoginDataMessage;
import view.*;

import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * This screen asks to the user to insert an IP, a port, a nickname and the number of players.
 * If inputs are valid, the active screen will be changed to {@link GodSelectionScreen}
 */
public abstract class ConnectionScreen extends Screen {

    /**
     * Maximum length for the username
     */
    public static final int MAX_USERNAME_LENGTH = 30;

    /**
     * Minimum length for the username
     */
    public static final int MIN_USERNAME_LENGTH = 3;
    private static final String CONF_ADDRESS = "configuration.json";

    private String ip;
    private String username;
    private int port;
    private int numberOfPlayers;

    private transient GodSelectionScreenBuilder screenBuilder;
    private transient List<String> players;
    private transient String activePlayerName;

    public ConnectionScreen(){
        super(null);
    }

    /**
     * ConnectionScreen constructor
     * @param view the view manager used
     */
    public ConnectionScreen(ViewManager view) {
        super(view);
        ip = "";
        username = "";
        port = 0;
        numberOfPlayers = 2;
        readConfigurationFromFile();
    }

    private void readConfigurationFromFile(){
        try{
            String confReader = new Scanner(new FileReader("configuration.json")).nextLine();
            Gson gson = new Gson();
            JsonObject parsed = gson.fromJson(confReader, JsonObject.class);
            ip = parsed.get("ip").getAsString();
            username = parsed.get("username").getAsString();
            port = parsed.get("port").getAsInt();
            numberOfPlayers = parsed.get("numberOfPlayers").getAsInt();
            Client.logger.info("configuration.json loaded!");
        } catch (Exception e){
            Client.logger.warning("Could not load configuration.json\n A new one will be generated" + e.getClass().getName());
        }
    }

    private void writeConfigurationToFile(){
        try{
            Gson gson = new Gson();
            FileWriter fw = new FileWriter(CONF_ADDRESS);
            gson.toJson(this,fw);
            fw.close();
        } catch (IOException e) {
            Client.logger.warning("Could not create configuration.json\n" +
                    e.getClass().getName());
        }
    }

    /**
     * @return true if the user has entered valid inputs
     */
    //Getter
    public final boolean readyToConnect(){
        return port > 0 && port < 65535 && username != null && (numberOfPlayers == 2 || numberOfPlayers == 3);
    }

    /**
     * @return ip attribute
     */
    protected final String getFieldIp() {
        return ip;
    }

    /**
     * @return username attribute
     */
    protected String getFieldUsername() {
        return username;
    }

    /**
     * @return port attribute
     */
    protected int getFieldPort() {
        return port;
    }

    /**
     * @return numberOfPlayers attribute
     */
    protected int getFieldNumberOfPlayers() {
        return numberOfPlayers;
    }

    //Logic fields setter
    /**
     * Sets the screen ip address
     * @param ip An ip address
     * @throws IllegalArgumentException if the ip string is empty
     */
    protected final void setIP(String ip) throws IllegalValueException{
        if(ip.isEmpty()) {
            throw new IllegalValueException(ClientErrorMessages.EMPTY_ADDRESS);
        }
        this.ip = ip;
    }

    /**
     * Sets the screen port address
     * @param port an integer between 0 and 65535, represent the port number of the remote server
     * @throws IllegalValueException if the port number is not valid
     */
    protected final void setPort(String port) throws IllegalValueException{
        int d = -1;
        try {
            d = Integer.parseInt(port);
        } catch (NumberFormatException nfe) {}
        if(d < 0 || d > 65535) {
            throw new IllegalValueException(ClientErrorMessages.INVALID_PORT);
        }
        this.port = d;
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
        if(!readyToConnect()) {
            throw new IllegalActionException(ClientErrorMessages.DATA_INCOMPLETE);
        }
        Screen nextScreen;
        screenBuilder = new GodSelectionScreenBuilder(view.getScreenFactory());
        writeConfigurationToFile();
        view.openConnection(ip,port);
        view.sendMessage(new LoginDataMessage(username,numberOfPlayers));
        view.setNumberOfPlayers(numberOfPlayers);
        nextScreen = screenBuilder.buildScreen();
        view.changeActiveScreen(nextScreen);
    }

    @Override
    @ServerListener
    public void receiveMatchFound(String playerName, List<String> players) {
        username = playerName; //Set server assigned username
        this.players = players;
        view.setThisPlayerName(username);
        view.setPlayersNames(players);
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
