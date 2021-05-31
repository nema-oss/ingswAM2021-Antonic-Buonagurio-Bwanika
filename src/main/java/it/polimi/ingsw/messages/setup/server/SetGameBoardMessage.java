package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.model.cards.DevelopmentDeck;
import it.polimi.ingsw.model.gameboard.Marble;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class SetGameBoardMessage implements SetupMessage, Serializable {

    private DevelopmentDeck[][] cardMarket;
    private Marble[][] market;
    private Marble freeMarble;

    public SetGameBoardMessage(DevelopmentDeck[][] cardMarket, Marble[][] market, Marble freeMarble) {
        this.cardMarket = cardMarket;
        this.market = market;
        this.freeMarble = freeMarble;
    }

    @Override
    public void execute(VirtualView virtualView) {

    }

    @Override
    public void execute(View view) {
        view.updateGameBoard(cardMarket,market,freeMarble);
    }

    public DevelopmentDeck[][] getCardMarket() {
        return cardMarket;
    }


    public Marble[][] getMarket() {
        return market;
    }



}
