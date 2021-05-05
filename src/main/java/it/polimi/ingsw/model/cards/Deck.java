package it.polimi.ingsw.model.cards;

/*
*sketch of class Deck - unfinished
 */

import it.polimi.ingsw.model.exception.NonExistentCardException;

public interface Deck <T>{

    T drawCard() throws NonExistentCardException;

    void shuffle();

    T getTop();
}
