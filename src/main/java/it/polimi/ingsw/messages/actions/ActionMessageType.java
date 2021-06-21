package it.polimi.ingsw.messages.actions;


/**
 * The type of message a client can send during its turn
 */
public enum ActionMessageType {

    BUY_RESOURCES,
    MOVE_DEPOSIT,
    PLACE_RESOURCES,
    DISCARD_RESOURCES,

    BUY_DEVELOPMENT,
    CARD_MARKET_UPDATE,

    ACTIVATE_PRODUCTION,
    ACTIVATE_BOARD_PRODUCTION,
    ACTIVATE_LEADERCARD_PRODUCTION,
    ACTIVATE_DEVELOPMENT_CARD_PRODUCTION,


    ACTIVATE_LEADERCARD,
    DISCARD_LEADERCARD,


    MOVE_ON_POPEROAD,

    CHEAT;

}
