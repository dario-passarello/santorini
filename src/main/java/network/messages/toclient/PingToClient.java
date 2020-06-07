package network.messages.toclient;

import network.messages.Message;
import view.screens.Screen;

import java.util.logging.Level;

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
