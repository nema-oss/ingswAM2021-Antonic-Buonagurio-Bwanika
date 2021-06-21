package it.polimi.ingsw.view.client.viewComponents;

import it.polimi.ingsw.model.cards.CardFactory;
import it.polimi.ingsw.model.cards.DevelopmentDeck;
import it.polimi.ingsw.model.gameboard.CardMarket;
import it.polimi.ingsw.model.gameboard.Marble;
import it.polimi.ingsw.model.gameboard.MarbleFactory;
import it.polimi.ingsw.model.gameboard.MarbleMarket;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class represents the game board client side
 */
public class ClientGameBoard implements Serializable {

    private final ClientCardMarket cardMarket;
    private final ClientMarbleMarket market;
    private static final int CARD_MARKET_ROW = 3;
    private static final int CARD_MARKET_COLUMNS = 4;
    private static final int MARBLE_MARKET_ROW = 3;
    private static final int MARBLE_MARKET_COLUMNS = 4;

    public ClientGameBoard() {
        DevelopmentDeck developmentDeck = new DevelopmentDeck(new CardFactory().getDevelopmentCards());
        ArrayList<Marble> marbles = new ArrayList<>(new MarbleFactory().getMarbles());

        this.cardMarket = new ClientCardMarket(developmentDeck, CARD_MARKET_ROW, CARD_MARKET_COLUMNS);
        this.market = new ClientMarbleMarket(MARBLE_MARKET_ROW, MARBLE_MARKET_COLUMNS, marbles);
    }

    /**
     * @return the card market
     */
    public ClientCardMarket getCardMarket() {
        return cardMarket;
    }

    /**
     * @return the marble market
     */
    public ClientMarbleMarket getMarket() {
        return market;
    }

    /**
     * @return the number of columns in the card market
     */
    public int getCardMarketColumns() {
        return CARD_MARKET_COLUMNS;
    }

    /**
     * @return the number of columns in the marble market
     */
    public int getMarbleMarketColumns() {
        return MARBLE_MARKET_COLUMNS;
    }

    /**
     * @return the number of rows in the card market
     */
    public int getCardMarketRow() {
        return CARD_MARKET_ROW;
    }

    /**
     * this method removes a card from a stack in the card market
     * @param i the row index of the card
     * @param j the column index of the card
     */
    public void remove(int i, int j){
        cardMarket.getStack(i,j).getListOfCards().remove(cardMarket.getStack(i,j).getListOfCards().size()-1);
    }
}
