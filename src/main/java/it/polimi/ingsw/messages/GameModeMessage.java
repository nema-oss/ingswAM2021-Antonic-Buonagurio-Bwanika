package it.polimi.ingsw.messages;

import java.io.Serializable;

public class GameModeMessage implements Serializable, Message {
    private final MessageType messageType;

    public GameModeMessage() {
        this.messageType = MessageType.GAME_MODE;
    }

    @Override
    public MessageType getType() {
        return messageType;
    }
}
