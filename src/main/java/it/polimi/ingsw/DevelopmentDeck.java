package it.polimi.ingsw;

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

}
