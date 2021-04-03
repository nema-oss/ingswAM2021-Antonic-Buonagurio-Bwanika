package it.polimi.ingsw.cards;

/*
*sketch of class Deck - unfinished
 */

public interface Deck <T>{

    T drawCard();

    void shuffle();

    T getTop();
}
