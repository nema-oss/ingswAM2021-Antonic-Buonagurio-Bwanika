package it.polimi.ingsw.messages.actions.server;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.actions.ActionMessage;
import it.polimi.ingsw.messages.actions.ActionMessageType;
import it.polimi.ingsw.model.gameboard.CardMarket;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

/**
 * message sent from server to clients to display the change after a player has bought from the market
 * @author Nemanja Antonic
 */

public class CardMarketUpdateMessage implements Serializable, ActionMessage {
    private final ActionMessageType messageType;
    private CardMarket cardMarket;

    /**
    * Server-side constructor to create the message
    * @param cardMarket: the updated cardMarket
    */
    public CardMarketUpdateMessage(CardMarket cardMarket) {
        this.cardMarket = cardMarket;
        messageType = ActionMessageType.CARD_MARKET_UPDATE;
    }

    /**
     * Execute the request client side
     * @param view: receiver view
     */
    public void execute(View view){
        //method in view to show the change
    }

    /**
     * Execute the request server side
     * @param virtualView: receiver view
     */
    public void execute(VirtualView virtualView){
        //method in virtualView
    }

    /**
     * Get the message type
     * @return the message type
     */
    public ActionMessageType getType() {
        return messageType;
    }


}
