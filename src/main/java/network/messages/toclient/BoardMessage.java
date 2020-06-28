package network.messages.toclient;

import model.Board;
import network.messages.Message;
import view.screens.Screen;

/**
 * A message to the client containing a {@link Board} object
 */
public class BoardMessage implements Message<Screen> {
    private final Board board;

    /**
     * Creates a BoardMessage
     * @param board A copy of the board to sent to the client
     */
    public BoardMessage(Board board) {
        this.board = board;
    }

    @Override
    public void execute(Screen target) {
        target.receiveBoard(board);
    }
}
