package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.exception.NonExistentCardException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * this class represents a deck of development cards
 */
public class DevelopmentDeck implements Deck<DevelopmentCard>, Serializable {

     private final ArrayList<DevelopmentCard> developmentDeck;

    public DevelopmentDeck(ArrayList<DevelopmentCard> cardList) {
        developmentDeck=cardList;
    }

    /**
     * @return the list of development cards in the deck
     */
    public ArrayList<DevelopmentCard> getListOfCards(){
        return developmentDeck;
    }

    @Override
     public DevelopmentCard drawCard() throws NonExistentCardException {
        if (developmentDeck.size()==0)
            throw new NonExistentCardException();
        else
            return developmentDeck.remove(0);
     }

     @Override
     public void shuffle(){
         Collections.shuffle(developmentDeck);
     }

    @Override
    public DevelopmentCard getTop() {
        return developmentDeck.get(0);
    }
}
