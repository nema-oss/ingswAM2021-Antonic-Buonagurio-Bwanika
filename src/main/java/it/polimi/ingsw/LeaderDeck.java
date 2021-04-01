package it.polimi.ingsw;

import java.util.ArrayList;

public class LeaderDeck implements Deck{

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
