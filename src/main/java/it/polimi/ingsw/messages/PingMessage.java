package it.polimi.ingsw.messages;

import it.polimi.ingsw.messages.actions.ActionMessageType;

import java.io.Serializable;

/**
 * the ping message that check if the connection between client and server is still active
 */

public class PingMessage implements Message, Serializable {
    private final MessageType messageType;

    /**
     * Server-side constructor to create the message
     */
    public PingMessage() {
        this.messageType = MessageType.PING;
    }


    /**
     * Get the message type
     * @return the message type
     */
    public MessageType getType() {
        return messageType;
    }
}
