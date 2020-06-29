package view.screens;

import model.Outcome;
import model.Player;
import view.ViewManager;

import java.util.List;

/**
 * Screen where the results of the finished game are shown
 */
public abstract class WinnerScreen extends Screen{

    /**
     * Creates the Winner Screen
     * @param view A link to the Players
     */
    public WinnerScreen(ViewManager view) {
        super(view);
    }

    /**
     * Get the name of the winner
     * @param players Get the name of the winner
     * @return Get the name of the winner
     */
    protected Player getWinner(List<Player> players){
        return players.stream().filter(p -> p.getStatus() == Outcome.WINNER).findAny().orElse(null);
    }

    /**
     * Closes the screen and goes to the menu
     */
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
