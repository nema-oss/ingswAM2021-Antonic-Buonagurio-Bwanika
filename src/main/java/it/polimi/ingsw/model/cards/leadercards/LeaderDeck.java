package it.polimi.ingsw.model.cards.leadercards;

import it.polimi.ingsw.model.cards.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderDeck implements Deck {

    private List<LeaderCard> listOfLeaderCard;

    public LeaderDeck(List<LeaderCard> cardsList) {
        listOfLeaderCard = cardsList;
    }

    public List<LeaderCard> getListOfCards(){
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
