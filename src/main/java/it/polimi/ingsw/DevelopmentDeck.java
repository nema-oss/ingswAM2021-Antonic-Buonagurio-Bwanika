package it.polimi.ingsw;

import java.util.ArrayList;

public class DevelopmentDeck extends Deck {

     private final ArrayList<DevelopmentCard> developmentDeck;
     public DevelopmentDeck(ArrayList<DevelopmentCard> cardsList){
         developmentDeck = cardsList;
     }

     public ArrayList<DevelopmentCard> getListOfCards(){
        return developmentDeck;
    }

     public DevelopmentCard drawCard(){
        return developmentDeck.get(0);
    }

     public void shuffle(){}
}
