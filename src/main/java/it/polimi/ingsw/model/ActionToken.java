package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.ActionTokenDeck;
import it.polimi.ingsw.model.gameboard.CardMarket;
import it.polimi.ingsw.model.player.PopeRoad;

public abstract class ActionToken {

    public String id;
    abstract boolean useEffect(PopeRoad popeRoad, CardMarket cardMarket, ActionTokenDeck actionTokenDeck);

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
