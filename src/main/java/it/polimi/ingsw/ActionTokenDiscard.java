package it.polimi.ingsw;

import it.polimi.ingsw.cards.DevelopmentCardType;
import it.polimi.ingsw.gameboard.CardMarket;
import it.polimi.ingsw.player.PopeRoad;

public class ActionTokenDiscard {

    private DevelopmentCardType type;
    private int amount;

    ActionTokenDiscard(DevelopmentCardType type, int amount){
        this.type = type;
        this.amount = amount;
    }

    public void useActionToken(PopeRoad popeRoad, CardMarket cardMarket){


    }

    public int getAmount() {
        return amount;
    }

    public DevelopmentCardType getType() {
        return type;
    }
}
