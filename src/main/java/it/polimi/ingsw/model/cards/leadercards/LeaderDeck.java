package it.polimi.ingsw.model.cards.leadercards;

import it.polimi.ingsw.model.cards.Deck;
import java.util.Collections;
import java.util.List;

/**
 * this class represents a deck of leader cards
 */
public class LeaderDeck implements Deck<LeaderCard> {

    private final List<LeaderCard> listOfLeaderCard;

    public LeaderDeck(List<LeaderCard> cardsList) {
        listOfLeaderCard = cardsList;
    }

    /**
     * @return the list of cards in the deck
     */
    public List<LeaderCard> getListOfCards(){
        return listOfLeaderCard;
    }

    @Override
    public LeaderCard drawCard(){
        return listOfLeaderCard.remove(0);
    }

    @Override
    public void shuffle(){
        Collections.shuffle(listOfLeaderCard);
    }

    @Override
    public LeaderCard getTop() {
        return listOfLeaderCard.get(0);
    }
}
