package view.screens;

public class GodSelectionScreenBuilder extends ScreenBuilder {

    private String activePlayer;
    private boolean receivedGameState;


    public GodSelectionScreenBuilder(ScreenFactory factory){
        super(factory);
        receivedGameState = false;
    }

    public synchronized void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
        notifyAll();
    }

    public synchronized void setStateReceived() {
        receivedGameState = true;
        notifyAll();
    }

    @Override
    public Screen buildScreen() {
        try{
            synchronized (this) {
                while (activePlayer == null || !receivedGameState)
                    wait();
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return screenFactory.getGodSelectionScreen(activePlayer);
    }
}
