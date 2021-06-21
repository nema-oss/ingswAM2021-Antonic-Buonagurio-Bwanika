package it.polimi.ingsw.messages.setup.server;

import it.polimi.ingsw.messages.setup.SetupMessage;
import it.polimi.ingsw.model.cards.DevelopmentDeck;
import it.polimi.ingsw.model.gameboard.Marble;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * message to setup the game board
 */
public class SetGameBoardMessage implements SetupMessage, Serializable {

    private final DevelopmentDeck[][] cardMarket;
    private final Marble[][] market;
    private final Marble freeMarble;

    public SetGameBoardMessage(DevelopmentDeck[][] cardMarket, Marble[][] market, Marble freeMarble) {
        this.cardMarket = cardMarket;
        this.market = market;
        this.freeMarble = freeMarble;
    }

    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    @Override
    public void execute(VirtualView virtualView) {

    }

    /**
     * Execute the request client side
     * @param view: receiver view
     */
    @Override
    public void execute(View view) {
        view.updateGameBoard(cardMarket,market,freeMarble);
    }

    /**
     * @return the game's card market
     */
    public DevelopmentDeck[][] getCardMarket() {
        return cardMarket;
    }


    /**
     * @return the game's marble market
     */
    public Marble[][] getMarket() {
        return market;
    }

}
