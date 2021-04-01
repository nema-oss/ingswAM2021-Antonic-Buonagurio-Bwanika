package it.polimi.ingsw.cards.leadercards;

import it.polimi.ingsw.cards.Deck;
import it.polimi.ingsw.cards.leadercards.LeaderCard;

import java.util.ArrayList;

public class LeaderDeck implements Deck {

    private ArrayList<LeaderCard> leaderDeck;

    public LeaderDeck(ArrayList<LeaderCard> cardsList) {
        leaderDeck = cardsList;
    }

    public ArrayList<LeaderCard> getListOfCards(){
        return leaderDeck;
    }

    public LeaderCard drawCard(){
        return leaderDeck.remove(0);
    }
}
