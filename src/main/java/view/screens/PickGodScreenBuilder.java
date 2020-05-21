package view.screens;

import java.util.ArrayList;
import java.util.List;

public class PickGodScreenBuilder extends ScreenBuilder {

    private String activePlayer;
    private List<String> availableGods;

    public PickGodScreenBuilder(ScreenFactory factory) {
        super(factory);
    }


    public synchronized void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
    }

    public synchronized void setAvailableGods(List<String> availableGods) {
        this.availableGods = new ArrayList<>(availableGods);
    }

    @Override
    public Screen buildScreen() {
        try{
            synchronized (this){
                while (activePlayer == null || availableGods == null)
                    wait();
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return screenFactory.getGodPickScreen(activePlayer,availableGods);
    }
}
