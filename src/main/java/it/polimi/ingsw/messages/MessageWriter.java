package it.polimi.ingsw.messages;

import it.polimi.ingsw.messages.actions.ActionMessage;
import it.polimi.ingsw.messages.actions.ActionMessageType;
import it.polimi.ingsw.messages.setup.SetupMessageType;

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
                String disconnectedNick = askServerDisconnectedNick();
                messageOut = new DisconnectionMessage(disconnectedNick);
        }

    }

    private void createMessage(SetupMessageType setupMessageType){

    }

    private void createMessage(ActionMessageType actionMessage){

    }

    private String askServerDisconnectedNick(){

    }

    public Message getMessage() {
        return messageOut;
    }
}
