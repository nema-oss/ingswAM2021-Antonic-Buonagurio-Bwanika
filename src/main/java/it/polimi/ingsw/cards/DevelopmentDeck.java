package it.polimi.ingsw.cards;

import it.polimi.ingsw.cards.Deck;
import it.polimi.ingsw.cards.DevelopmentCard;

import java.util.ArrayList;
import java.util.Collections;

public class DevelopmentDeck implements Deck {

     private final ArrayList<DevelopmentCard> developmentDeck;

     public DevelopmentDeck(ArrayList<DevelopmentCard> cardsList){
         developmentDeck = cardsList;
     }

     public ArrayList<DevelopmentCard> getListOfCards(){
        return developmentDeck;
    }

     public DevelopmentCard drawCard(){
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
