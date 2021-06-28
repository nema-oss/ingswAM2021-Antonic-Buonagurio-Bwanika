package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.ActionTokenDeck;
import it.polimi.ingsw.model.cards.DevelopmentCardType;
import it.polimi.ingsw.model.gameboard.CardMarket;
import it.polimi.ingsw.model.player.PopeRoad;

/**
 * this class represents an action token of type discard
 */
public class ActionTokenDiscard extends ActionToken{

    private DevelopmentCardType type;
    private int amount;

    ActionTokenDiscard(DevelopmentCardType type, int amount){
        this.type = type;
        this.amount = amount;
    }

    @Override
    public boolean useEffect(PopeRoad popeRoad, CardMarket cardMarket, ActionTokenDeck actionTokenDeck){
        return cardMarket.discardCard(type, amount);
    }

    /**
     * @return amount of crads to discard
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @return type of cards to discard
     */
    public DevelopmentCardType getType() {
        return type;
    }
}
