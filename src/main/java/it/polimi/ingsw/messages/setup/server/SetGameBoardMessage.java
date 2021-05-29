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

    public SetGameBoardMessage(DevelopmentDeck[][] cardMarket, Marble[][] market) {
        this.cardMarket = cardMarket;
        this.market = market;
    }

    @Override
    public void execute(VirtualView virtualView) {

    }

    @Override
    public void execute(View view) {
        view.updateGameBoard(cardMarket,market);
    }

    public DevelopmentDeck[][] getCardMarket() {
        return cardMarket;
    }


    public Marble[][] getMarket() {
        return market;
    }

}
