package view.screens;

import model.Board;
import model.Builder;
import model.Player;
import view.ViewManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class BoardScreen extends Screen {

    private String activePlayer;
    private List<Player> players;
    private Board board;
    private List<Builder> allBuilders;

    public BoardScreen(ViewManager view, String activePlayer, List<Player> players) {
        super(view);
        this.activePlayer = activePlayer;
        this.players = new ArrayList<>(players);
    }


}
