package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.exception.NonExistentCardException;
import java.util.ArrayList;
import java.util.Collections;

public class DevelopmentDeck implements Deck {

     private ArrayList<DevelopmentCard> developmentDeck;

    public DevelopmentDeck(ArrayList<DevelopmentCard> cardList) {
        developmentDeck=cardList;
    }

    public ArrayList<DevelopmentCard> getListOfCards(){
        return developmentDeck;
    }

     public DevelopmentCard drawCard() throws NonExistentCardException {
        if (developmentDeck.size()==0)
            throw new NonExistentCardException();
        else
            return developmentDeck.remove(0);
     }

     public void shuffle(){
         Collections.shuffle(developmentDeck);
     }

    @Override
    public DevelopmentCard getTop() {
        return developmentDeck.get(0);
    }
}
