package it.polimi.ingsw.controller;

/**
 * this enumeration lists the possible errors in game phase
 */
public enum Error {
    GENERIC,
    DEPOSIT_IS_FULL,
    INSUFFICIENT_PAYMENT,
    CARD_DOESNT_EXIST,
    NOT_YOUR_TURN,
    GAME_ALREADY_FULL,
    NICKNAME_ALREADY_EXISTS,
    NON_VALID_DEPOSIT_SWAP,
    PRODUCTION_REQUIREMENTS_ERROR,
    INVALID_ACTION,
    WRONG_GAME_PHASE
}
