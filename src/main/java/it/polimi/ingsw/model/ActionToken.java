package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.ActionTokenDeck;
import it.polimi.ingsw.model.gameboard.CardMarket;
import it.polimi.ingsw.model.player.PopeRoad;

import java.io.Serializable;

/**
 * this class represents an action token
 */
public abstract class ActionToken implements Serializable {

    public String id;

    /**
     * this method activates a token effect
     * @param popeRoad the pope road to move on
     * @param cardMarket the card market to remove the cards from
     * @param actionTokenDeck the deck of tokens to shuffle
     * @return
     */
    abstract boolean useEffect(PopeRoad popeRoad, CardMarket cardMarket, ActionTokenDeck actionTokenDeck);

    /**
     * this method sets the id of a token
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the token's id
     */
    public String getId() {
        return id;
    }
}
