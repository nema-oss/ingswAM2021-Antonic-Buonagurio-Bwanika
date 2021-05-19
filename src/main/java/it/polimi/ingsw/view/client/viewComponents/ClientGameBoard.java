package it.polimi.ingsw.view.client.viewComponents;

import it.polimi.ingsw.model.cards.CardFactory;
import it.polimi.ingsw.model.cards.DevelopmentDeck;
import it.polimi.ingsw.model.gameboard.CardMarket;
import it.polimi.ingsw.model.gameboard.Marble;
import it.polimi.ingsw.model.gameboard.MarbleFactory;
import it.polimi.ingsw.model.gameboard.MarbleMarket;

import java.util.ArrayList;

public class ClientGameBoard {

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

    public ClientCardMarket getCardMarket() {
        return cardMarket;
    }

    public ClientMarbleMarket getMarket() {
        return market;
    }

    public int getCardMarketColumns() {
        return CARD_MARKET_COLUMNS;
    }

    public int getMarbleMarketColumns() {
        return MARBLE_MARKET_COLUMNS;
    }

    public int getCardMarketRow() {
        return CARD_MARKET_ROW;
    }
}
