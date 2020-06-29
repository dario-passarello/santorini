package view.screens;

import model.Player;

import java.util.List;

/**
 * Builds the {@link WinnerScreen}
 */
public class WinnerScreenBuilder extends ScreenBuilder{

    private List<Player> players;

    /**
     * Creates
     * @param factory a factory
     */
    public WinnerScreenBuilder(ScreenFactory factory) {
        super(factory);
    }

    /**
     * Name
     * @param players players
     */
    public void setPlayers(List<Player> players){
        this.players = players;
    }

    @Override
    public Screen buildScreen() {
        return screenFactory.getWinnerScreen(players);
    }
}
