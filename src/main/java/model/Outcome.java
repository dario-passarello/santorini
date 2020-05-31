package model;

public enum Outcome {
    IN_GAME(true),
    WINNER(false),
    LOSER(false),
    DISCONNECTED(false);


    private final boolean alive;

    Outcome(boolean alive){
        this.alive = alive;
    }

    public final boolean isAlive(){
        return alive;
    }

}
