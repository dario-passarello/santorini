package network.messages.toserver;

import network.ClientHandler;
import network.messages.Message;
import utils.Coordinate;
import view.RemoteView;

public class SelectCoordinateMessage implements Message<RemoteView> {

    private final Coordinate coordinate;
    private final boolean useGodPower;

    public SelectCoordinateMessage(Coordinate coordinate, boolean useGodPower){
        this.coordinate = coordinate;
        this.useGodPower = useGodPower;
    }


    @Override
    public void execute(RemoteView target) {
        target.getController().turn().selectCoordinate(target,coordinate,useGodPower);
    }
}
