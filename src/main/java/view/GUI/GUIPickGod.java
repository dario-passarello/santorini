package view.GUI;

import model.Player;
import view.ViewManager;
import view.screens.PickGodScreen;

import java.util.List;

public class GUIPickGod extends PickGodScreen {
    public GUIPickGod(ViewManager view, String firstActivePlayer, List<String> availableGods) {
        super(view, firstActivePlayer, availableGods);
    }

    @Override
    public void onScreenOpen() {

    }

    @Override
    public void onScreenClose() {

    }



    @Override
    public void receivePlayerList(List<Player> list){
        super.receivePlayerList(list);
        super.getGodsRemaining();
    }
}
