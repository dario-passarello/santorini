package network.messages.toclient;

import model.Board;
import network.messages.Message;
import view.ClientView;

public class BoardMessage implements Message<ClientView> {
    private final Board board;

    public BoardMessage(Board board) {
        this.board = board;
    }

    @Override
    public void execute(ClientView target) {
        target.receiveBoard(board);
    }
}
