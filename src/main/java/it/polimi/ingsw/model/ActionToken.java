package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.ActionTokenDeck;
import it.polimi.ingsw.model.gameboard.CardMarket;
import it.polimi.ingsw.model.player.PopeRoad;

public abstract class ActionToken {

    abstract boolean useEffect(PopeRoad popeRoad, CardMarket cardMarket, ActionTokenDeck actionTokenDeck);

}
