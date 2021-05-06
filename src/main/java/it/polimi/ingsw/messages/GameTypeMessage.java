package it.polimi.ingsw.messages;

import java.io.Serializable;

public class GameTypeMessage implements Serializable, Message {
    private final MessageType messageType;

    public GameTypeMessage() {
        this.messageType = MessageType.GAMETYPE;
    }

    @Override
    public MessageType getType() {
        return messageType;
    }
}
