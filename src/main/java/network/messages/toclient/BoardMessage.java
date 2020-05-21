package network.messages.toclient;

import model.Board;
import network.messages.Message;
import view.screens.Screen;

public class BoardMessage implements Message<Screen> {
    private final Board board;

    public BoardMessage(Board board) {
        this.board = board;
    }

    @Override
    public void execute(Screen target) {
        target.receiveBoard(board);
    }
}
