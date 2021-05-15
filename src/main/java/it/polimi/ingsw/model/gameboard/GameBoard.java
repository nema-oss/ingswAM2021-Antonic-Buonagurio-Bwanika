package it.polimi.ingsw.model.gameboard;

import it.polimi.ingsw.model.cards.CardFactory;
import it.polimi.ingsw.model.cards.DevelopmentDeck;

import java.util.ArrayList;

/*
 * this class represents the general game board, containing the card market and the marble market.
 * @author Chiara Buonagurio
*/

public class GameBoard {

    private final CardMarket cardMarket;
    private final MarbleMarket market;
    private static final int CARD_MARKET_ROW = 3;
    private static final int CARD_MARKET_COLUMNS = 4;
    private static final int MARBLE_MARKET_ROW = 3;
    private static final int MARBLE_MARKET_COLUMNS = 4;

    public GameBoard() {
        DevelopmentDeck developmentDeck = new DevelopmentDeck(new CardFactory().getDevelopmentCards());
        ArrayList<Marble> marbles = new ArrayList<>(new MarbleFactory().getMarbles());

        this.cardMarket = new CardMarket(developmentDeck, CARD_MARKET_ROW, CARD_MARKET_COLUMNS);
        this.market = new MarbleMarket(MARBLE_MARKET_ROW, MARBLE_MARKET_COLUMNS, marbles);
    }

    public CardMarket getCardMarket() {
        return cardMarket;
    }

    public MarbleMarket getMarket() {
        return market;
    }

    public static int getCardMarketRow() {
        return CARD_MARKET_ROW;
    }

    public static int getCardMarketColumns() {
        return CARD_MARKET_COLUMNS;
    }

    public static int getMarbleMarketRow() {
        return MARBLE_MARKET_ROW;
    }

    public static int getMarbleMarketColumns() {
        return MARBLE_MARKET_COLUMNS;
    }
}
