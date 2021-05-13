package it.polimi.ingsw.messages.utils;

import it.polimi.ingsw.messages.setup.DisconnectionMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MessageType;
import it.polimi.ingsw.messages.actions.*;
import it.polimi.ingsw.messages.actions.server.CardMarketUpdateMessage;
import it.polimi.ingsw.messages.actions.server.MoveOnPopeRoadMessage;
import it.polimi.ingsw.messages.setup.*;
import it.polimi.ingsw.messages.setup.server.LoginDoneMessage;
import it.polimi.ingsw.messages.setup.server.DoLoginMessage;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.gameboard.CardMarket;
import it.polimi.ingsw.model.gameboard.Resource;

import java.util.ArrayList;

/**
 * This class manage the creation of different type of messages
 */
public class MessageWriter{

    private Object messageType;
    private Message messageOut;

    public MessageWriter(MessageType messageType) {
        this.messageType = messageType;
        createMessage(messageType);
    }

    public MessageWriter(SetupMessageType messageType) {
        this.messageType = messageType;
        createMessage(messageType);
    }

    public MessageWriter(ActionMessageType messageType) {
        this.messageType = messageType;
        createMessage(messageType);
    }

    /**
     * This method create a Message of type
     * @param messageType
     */
    private void createMessage(MessageType messageType) {
        switch (messageType){
            case PING:
                messageOut = new PingMessage();
                break;
            case DISCONNECTION:
                String disconnectedNick = askServerDisconnectedNick();
                messageOut = new DisconnectionMessage(disconnectedNick);
                break;
        }

    }

    private void createMessage(SetupMessageType setupMessageType){
        switch (setupMessageType){
            case LOGIN_REQUEST:
                //messageOut = new LoginRequest(askClientNick());
                break;
            case LOGIN:
                messageOut = new DoLoginMessage();
                break;
            case LOGIN_DONE:
                messageOut = new LoginDoneMessage(true);
                break;
            case GAME_MODE:
                messageOut = new GameModeMessage(askClientNumPlayers());
                break;
        }
    }

    private void createMessage(ActionMessageType actionMessage){
        switch(actionMessage){
            case CARD_MARKET_UPDATE:
                messageOut = new CardMarketUpdateMessage(askServerNewMarket());
                break;
            case MOVE_ON_POPEROAD:
                messageOut = new MoveOnPopeRoadMessage(askServerMovesPopeRoad());
                break;
            case ACTIVATE_PRODUCTION:
                messageOut = new ActivateProductionMessage();
                break;
            case DISCARD_LEADERCARD:
                messageOut = new DiscardLeaderCardMessage(askClientLeaderDiscardChoice());
                break;
            case PLACE_RESOURCES:
                messageOut = new PlaceResourcesMessage(askServerBoughtResources(), askClientToShelves());
                break;
        }
    }

    public Message getMessage() {
        return messageOut;
    }

    private ArrayList<Resource> askServerBoughtResources() {
        return null;
    }

    private ArrayList<Integer> askClientToShelves() {
        return null;

    }


    private LeaderCard askClientLeaderDiscardChoice() {
        return null;
    }

    private String askClientNick(){
        return null;
    }

    private String askServerDisconnectedNick(){
        return "ciao";
    }

    private int askClientNumPlayers(){
        return 0;
    }

    private CardMarket askServerNewMarket(){
        return null;
    }

    private int askServerMovesPopeRoad(){
        return 0;
    }

}
