package it.polimi.ingsw;

import it.polimi.ingsw.cards.ActionTokenDeck;
import it.polimi.ingsw.gameboard.CardMarket;
import it.polimi.ingsw.player.PopeRoad;

public abstract class ActionToken {

    abstract boolean useEffect(PopeRoad popeRoad, CardMarket cardMarket, ActionTokenDeck actionTokenDeck);

}
