package it.polimi.ingsw.gameboard;

import it.polimi.ingsw.cards.CardFactory;
import it.polimi.ingsw.cards.DevelopmentDeck;

import java.util.ArrayList;

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


}
