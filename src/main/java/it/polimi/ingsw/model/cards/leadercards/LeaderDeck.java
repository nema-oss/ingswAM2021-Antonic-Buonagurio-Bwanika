package it.polimi.ingsw.model.cards.leadercards;

import it.polimi.ingsw.model.cards.Deck;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderDeck implements Deck {

    private ArrayList<LeaderCard> listOfLeaderCard;

    public LeaderDeck(ArrayList<LeaderCard> cardsList) {
        listOfLeaderCard = cardsList;
    }

    public ArrayList<LeaderCard> getListOfCards(){
        return listOfLeaderCard;
    }

    public LeaderCard drawCard(){
        return listOfLeaderCard.remove(0);
    }

    public void shuffle(){
        Collections.shuffle(listOfLeaderCard);
    }

    @Override
    public LeaderCard getTop() {
        return listOfLeaderCard.get(0);
    }
}
