package it.polimi.ingsw.messages;

import it.polimi.ingsw.messages.actions.*;
import it.polimi.ingsw.messages.actions.server.CardMarketUpdateMessage;
import it.polimi.ingsw.messages.actions.server.MoveOnPopeRoadMessage;
import it.polimi.ingsw.messages.setup.*;
import it.polimi.ingsw.messages.setup.client.LoginRequest;
import it.polimi.ingsw.messages.setup.server.LoginDoneMessage;
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
            case DISCONNECTION:
               // String disconnectedNick = askServerDisconnectedNick();
                //messageOut = new DisconnectionMessage(disconnectedNick);
        }

    }

    private void createMessage(SetupMessageType setupMessageType){
        switch (setupMessageType){
            //case LOGIN_REQUEST:
              //  messageOut = new LoginRequest(askClientNick());
            case LOGIN:
                messageOut = new LoginMessage();
            case LOGIN_DONE:
                messageOut = new LoginDoneMessage(true);
            //case GAME_MODE:
              //  messageOut = new GameModeMessage(askClientNumPlayers());
        }
    }

    private void createMessage(ActionMessageType actionMessage){
        /*switch(actionMessage){
            case CARD_MARKET_UPDATE:
                messageOut = new CardMarketUpdateMessage(askServerNewMarket());
            case MOVE_ON_POPEROAD:
                messageOut = new MoveOnPopeRoadMessage(askServerMovesPopeRoad());
            case ACTIVATE_PRODUCTION:
                messageOut = new ActivateProductionMessage();
            case DISCARD_LEADERCARD:
                messageOut = new DiscardLeaderCardMessage(askClientLeaderDiscardChoice());
            case PLACE_RESOURCES:
                messageOut = new PlaceResourcesMessage(askServerBoughtResources(), askClientToShelves());
        }*/
    }

    public Message getMessage() {
        return messageOut;
    }
    /*
    private ArrayList<Resource> askServerBoughtResources() {
    }

    private ArrayList<Integer> askClientToShelves() {
    }


    private LeaderCard askClientLeaderDiscardChoice() {
    }

    private String askClientNick(){

    }

    private String askServerDisconnectedNick(){
        return "ciao";
    }

    private int askClientNumPlayers(){

    }

    private CardMarket askServerNewMarket(){

    }

    private int askServerMovesPopeRoad(){

    }


    public Message getMessage() {
        return messageOut;
    }*/
}
