package network.messages.toclient;

import model.Board;
import network.messages.Message;
import view.ViewManager;

public class BoardMessage implements Message<ViewManager> {
    private final Board board;

    public BoardMessage(Board board) {
        this.board = board;
    }

    @Override
    public void execute(ViewManager target) {
        target.receiveBoard(board);
    }
}
