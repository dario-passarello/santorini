package model;

/**
 * Represents all possible statuses of a player
 */
public enum Outcome {
    /**
     * The player is playing the game
     */
    IN_GAME(true),
    /**
     * The player has won the game
     */
    WINNER(false),
    /**
     * The player has lost the game but is already spectating the game
     */
    LOSER(false),
    /**
     * The player has lost connection to the server
     */
    DISCONNECTED(false);


    private final boolean alive;

    Outcome(boolean alive){
        this.alive = alive;
    }

    /**
     * @return true if the player is an active participant in the game
     */
    public final boolean isActive(){
        return alive;
    }

}
