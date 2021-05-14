package it.polimi.ingsw.messages.actions;


/**
 * The type of message a client can send during its turn
 */
public enum ActionMessageType {

    //Buy resource action
    BUY_RESOURCES,
    MOVE_DEPOSIT,
    PLACE_RESOURCES,
    DISCARD_RESOURCES,

    //Buy card
    BUY_DEVELOPMENT,
    CARD_MARKET_UPDATE,

    //Activate production
    ACTIVATE_PRODUCTION, // this is unnecessary maybe
    ACTIVATE_BOARD_PRODUCTION,
    ACTIVATE_LEADERCARD_PRODUCTION,
    ACTIVATE_DEVELOPMENT_CARD_PRODUCTION,

    //Leader action
    ACTIVATE_LEADERCARD,
    DISCARD_LEADERCARD,


    MOVE_ON_POPEROAD; // this is useless

}
