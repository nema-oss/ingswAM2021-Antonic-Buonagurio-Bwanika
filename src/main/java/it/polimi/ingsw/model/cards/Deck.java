package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.exception.NonExistentCardException;

/**
 * this class represents a deck
 */

public interface Deck <T> {

    /**
     * this method draws an element from the deck
     * @return the object drawn
     * @throws NonExistentCardException if the object does not exist
     */
    T drawCard() throws NonExistentCardException;

    /**
     * this method shuffles the deck
     */
    void shuffle();

    /**
     * @return the top of the deck
     */
    T getTop();
}
