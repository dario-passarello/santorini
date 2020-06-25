package view.screens;

import model.Outcome;
import model.Player;
import view.ViewManager;

import java.util.List;

public abstract class WinnerScreen extends Screen{

    public WinnerScreen(ViewManager view) {
        super(view);
    }

    protected Player getWinner(List<Player> players){
        return players.stream().filter(p -> p.getStatus() == Outcome.WINNER).findAny().orElse(null);
    }

    public final void goToMenuScreen(){
        view.changeActiveScreen(view.getScreenFactory().getMenuScreen());
    }

    @Override
    public void onScreenOpen() {
        view.closeConnection();
    }

    @Override
    public void onScreenClose() {

    }
}
