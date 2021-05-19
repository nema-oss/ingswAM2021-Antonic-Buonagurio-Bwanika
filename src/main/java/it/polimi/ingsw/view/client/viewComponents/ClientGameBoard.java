package it.polimi.ingsw.view.client.viewComponents;

import it.polimi.ingsw.model.cards.CardFactory;
import it.polimi.ingsw.model.cards.DevelopmentDeck;
import it.polimi.ingsw.model.gameboard.CardMarket;
import it.polimi.ingsw.model.gameboard.Marble;
import it.polimi.ingsw.model.gameboard.MarbleFactory;
import it.polimi.ingsw.model.gameboard.MarbleMarket;

import java.util.ArrayList;

public class ClientGameBoard {

    private final CardMarket cardMarket;
    private final MarbleMarket market;
    private static final int CARD_MARKET_ROW = 3;
    private static final int CARD_MARKET_COLUMNS = 4;
    private static final int MARBLE_MARKET_ROW = 3;
    private static final int MARBLE_MARKET_COLUMNS = 4;

    public ClientGameBoard() {
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
