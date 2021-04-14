package it.polimi.ingsw;

import it.polimi.ingsw.cards.ActionTokenDeck;
import it.polimi.ingsw.cards.DevelopmentCardType;
import it.polimi.ingsw.exception.NonExistentCardException;
import it.polimi.ingsw.gameboard.CardMarket;
import it.polimi.ingsw.player.PopeRoad;

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

    public int getAmount() {
        return amount;
    }

    public DevelopmentCardType getType() {
        return type;
    }
}
