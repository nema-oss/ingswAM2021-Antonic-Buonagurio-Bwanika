package it.polimi.ingsw.gameboard;

import it.polimi.ingsw.cards.DevelopmentDeck;

import java.util.ArrayList;

public class GameBoard {
    private final CardMarket cardMarket;
    private final MarbleMarket market;

    public GameBoard(DevelopmentDeck developmentDeck, int cardMarketRows, int cardMarketColumns, int marbleMarketRows, int marbleMarketColumns, ArrayList<Marble> marbles) {

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
