package it.polimi.ingsw.messages;

import java.io.Serializable;

/**
 * the ping message that check if the connection between client and server is still active
 */

public class PingMessage implements Message, Serializable {
    private final MessageType messageType;

    public PingMessage() {
        this.messageType = MessageType.PING;
    }


    @Override
    public MessageType getType() {
        return messageType;
    }
}
