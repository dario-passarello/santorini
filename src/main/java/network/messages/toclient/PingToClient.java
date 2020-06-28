package network.messages.toclient;

import network.messages.Message;
import view.screens.Screen;

import java.util.logging.Level;

/**
 * A ping message to check if the client connection is still alive
 */
public class PingToClient implements Message<Screen> {

    @Override
    public void execute(Screen target) {
        //Do nothing, it's only a ping
    }

    @Override
    public Level getLoggerLever(){
        return Level.FINE;
    }
}
