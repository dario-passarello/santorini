package network.messages.toserver;

import network.ClientHandler;
import network.messages.Message;
import utils.Coordinate;
import view.RemoteView;

/**
 * A message sent to server when a square is selected to do an action
 */
public class SelectCoordinateMessage implements Message<RemoteView> {

    private final Coordinate coordinate;
    private final boolean useGodPower;

    /**
     * Create a new isntance
     * @param coordinate The coordinate
     * @param useGodPower True if the power
     */
    public SelectCoordinateMessage(Coordinate coordinate, boolean useGodPower){
        this.coordinate = coordinate;
        this.useGodPower = useGodPower;
    }


    @Override
    public void execute(RemoteView target) {
        target.getController().turn().selectCoordinate(target,coordinate,useGodPower);
    }
}
