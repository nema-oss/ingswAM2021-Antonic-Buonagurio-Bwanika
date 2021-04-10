package it.polimi.ingsw.gameboard;

import it.polimi.ingsw.cards.CardFactory;
import it.polimi.ingsw.cards.DevelopmentDeck;

import java.util.ArrayList;

public class GameBoard {
    private final CardMarket cardMarket;
    private final MarbleMarket market;

    public GameBoard(int cardMarketRows, int cardMarketColumns, int marbleMarketRows, int marbleMarketColumns) {
        DevelopmentDeck developmentDeck = new DevelopmentDeck(new CardFactory().getDevelopmentCards());
        ArrayList<Marble> marbles = new ArrayList<>(new MarbleFactory().getMarbles());

        this.cardMarket = new CardMarket(developmentDeck, cardMarketRows, cardMarketColumns);
        this.market = new MarbleMarket(marbleMarketRows, marbleMarketColumns, marbles);
    }

    public CardMarket getCardMarket() {
        return cardMarket;
    }

    public MarbleMarket getMarket() {
        return market;
    }


}
