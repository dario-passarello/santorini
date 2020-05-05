package utils;

public enum MessageType {

    //Configuration
    NICKNAME_REQUEST,
    CONNECTION_CONFIG,
    PLAYER_NUMBER,

    //State Messages
    GOD_SELECTION,
    GOD_PICK,
    PLACE_BUILDER,
    TURN,
    END_GAME,


    //Network messages
    CLIENT_DISCONNECTION,
    CLIENT_DISCONNECTED,
    SERVER_IP,

    //Game Messages
    GOD_LIST,
    GOD_CHOICE,
    BUILDER_PLACEMENT,

    //Turn Messages
    FIRST_ACTION,
    COORDINATE,
    END_PHASE,


    //Error Messages
    STATE_ERROR,
    ILLEGAL_ERROR,




}
