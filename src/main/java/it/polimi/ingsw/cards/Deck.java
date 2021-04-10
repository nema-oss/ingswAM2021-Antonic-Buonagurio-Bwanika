package it.polimi.ingsw.cards;

/*
*sketch of class Deck - unfinished
 */

import it.polimi.ingsw.exception.NonExistentCardException;

public interface Deck <T>{

    T drawCard() throws NonExistentCardException;

    void shuffle();

    T getTop();
}
