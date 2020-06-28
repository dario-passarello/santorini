
package model;

/**
 * contains specific error messages that can be thrown
 */
public class ErrorMessage {
    public static final String COORDINATE_NOT_VALID = "Coordinate outside of the board";
    public static final String WRONG_BUILD_OWNER = "Current players does not control the builder";
    public static final String ILLEGAL_MOVE = "Builder can't move in the selected square";
    public static final String NO_SPECIAL_POWER = "God has no special power in current phase";
    public static final String ALREADY_DONE = "The request is already done";
    public static final String PLAYER_NUMBER_ERROR = "Match should have 2 or 3 players";
    public static final String PLAYER_NOT_FOUND = "No such player in the game";
    public static final String PLAYER_ALREADY_REMOVED = "The player was already removed from the game";
    public static final String MALFORMED_BOARD = "Square without builder, malformed board";
    public static final String GOD_NOT_EXISTS = "Invalid God Name";
    public static final String NOT_OPTIONAL_STATE = "The state is not optional, it can't be skipped";
    public static final String GOD_NOT_ASSIGNED = "Player has no god assigned";
    public static final String UNVALID_GOD_ID = "Builder ID is not valid";
}
