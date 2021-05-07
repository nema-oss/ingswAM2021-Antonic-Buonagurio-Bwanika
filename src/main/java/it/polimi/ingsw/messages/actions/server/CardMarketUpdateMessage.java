package it.polimi.ingsw.messages.actions.server;

import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.actions.ActionMessage;
import it.polimi.ingsw.model.gameboard.CardMarket;
import it.polimi.ingsw.view.client.View;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.Serializable;

public class CardMarketUpdateMessage implements Serializable, ActionMessage {
    private final MessageType messageType;
    private CardMarket cardMarket;

    public CardMarketUpdateMessage(CardMarket cardMarket) {
        this.cardMarket = cardMarket;
        messageType = MessageType.CARD_MARKET_UPDATE;
    }

    public void execute(View view){
        //method in view to show the change
    }

    public void execute(VirtualView virtualView){
        //method in virtualView
    }
    @Override
    public MessageType getType() {
        return messageType;
    }
}
