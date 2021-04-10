package it.polimi.ingsw.cards;

/*
*sketch of class Deck - unfinished
 */

import it.polimi.ingsw.exception.NonexistentCardException;

public interface Deck <T>{

    T drawCard() throws NonexistentCardException;

    void shuffle();

    T getTop();
}
