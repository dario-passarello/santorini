package view.screens;

import model.Player;

import java.util.List;

public class WinnerScreenBuilder extends ScreenBuilder{

    private List<Player> players;

    public WinnerScreenBuilder(ScreenFactory factory) {
        super(factory);
    }

    public void setPlayers(List<Player> players){
        this.players = players;
    }

    @Override
    public Screen buildScreen() {
        return screenFactory.getWinnerScreen(players);
    }
}
